package addon.score.cal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Xiwei Sun, Guo-Bo Chen, chenguobo@gmail.com
 */

import org.apache.commons.math3.linear.*;

public class MultiClassLogisticRegression {
	int category;
	double[][] Y;
	double[][] P;
	double[][] X;
	double[] B;
	double threshold = 0.001;
	double LogLikelihood_old;
	double LogLikelihood_new;

         // y 起始值为1，2，3，，，，
        
	public MultiClassLogisticRegression(double[] y, double[][] x, int k) {
		category = k;
		Y = new double[y.length][k - 1];
		P = new double[y.length][k - 1];
                
		for (int i = 0; i < y.length; i++) {
			if (y[i] < k) {
				Y[i][(new Double(y[i])).intValue()-1] = 1;
			}
		}
		X = new double[x.length * (k - 1)][x[0].length * (k - 1)];
		for (int i = 0; i < k - 1; i++) {
			for (int j = 0; j < x.length; j++) {
				System.arraycopy(x[j], 0, X[i * x.length + j], x[j].length * i, x[j].length);
			}
		}
		B = new double[x[0].length * (k - 1)];
	}

	public void MLE() {
		RealMatrix Matrix_X = new Array2DRowRealMatrix(X);
		RealMatrix Matrix_XT = Matrix_X.transpose();
		int iteration = 20;
		int iter = 0;
		RealMatrix B_old = new Array2DRowRealMatrix(B);
		RealMatrix B_new = new Array2DRowRealMatrix(B);
		calculate_P(B);
		LogLikelihood_old = Likelihood();
		do {
			B_old = B_new;
			LogLikelihood_old = LogLikelihood_new;
			B = B_old.getColumn(0);
			RealMatrix Matrix_W = new Array2DRowRealMatrix(getWMatrix());
			RealMatrix Matrix_XT_W = Matrix_XT.multiply(Matrix_W);
                        
			RealMatrix Matrix_XT_W_X = Matrix_XT_W.multiply(Matrix_X);
                        
			//RealMatrix Inv_XT_W_X = new LUDecomposition(Matrix_XT_W_X).getSolver().getInverse();
                        RealMatrix Inv_XT_W_X = new SingularValueDecomposition(Matrix_XT_W_X).getSolver().getInverse();
			RealMatrix Inv_XT_W_X_XT = Inv_XT_W_X.multiply(Matrix_XT);
			RealMatrix Vector_Pre = new Array2DRowRealMatrix(Residual1D());
			RealMatrix Vector_H = Inv_XT_W_X_XT.multiply(Vector_Pre);
			B_new = B_old.add(Vector_H);
			calculate_P(B_new.getColumn(0));
			LogLikelihood_new = Likelihood();
			//System.out.println(iter + "--->" + B_new);
			//System.out.println("LogLikelihood_old " + LogLikelihood_old + ", LogLikelihood_new " + LogLikelihood_new);
		} while (iter++ < iteration && Math.abs(LogLikelihood_old - LogLikelihood_new) > threshold);
		System.out.println();
	}

	public void calculate_P(double[] b) {
		for (int i = 0; i < P.length; i++) {
			double[] p_ele = new double[category - 1];
			double sum = 0;
			for (int j = 0; j < P[i].length; j++) {
				double s = 0;
				for (int k = 0; k < b.length; k++) {
//					s += X[P.length * j + i][b.length * j + k] * b[k];
                                       s += X[P.length * j + i][k] * b[k]; //sun modified
				}
				p_ele[j] = Math.exp(s);
				sum += p_ele[j];
			}
			for (int j = 0; j < P[i].length; j++) {
				P[i][j] = p_ele[j] / (1 + sum);
			}
		}
	}

	public double[][] Residual2D() {
		double[][] res = new double[Y.length][Y[0].length];
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[i].length; j++) {
				res[i][j] = Y[i][j] - P[i][j];
			}
		}
		return res;
	}
        public double[] Residual1D(){
        double []res=new double[Y.length*(category-1)];
        for (int i = 0; i < Y.length; i++) {
			for (int j = 0; j < Y[i].length; j++) {
				res[j*Y.length+i]= Y[i][j] - P[i][j];
			}
		}
        return res;
        }
//xwsun     
        public double[] avgResidual1D(){
        
            double [] res = new double[Y.length];
            
            for (int i = 0; i < res.length; i++) {
                double temp = 0;
			for (int j = 0; j < category-1; j++) {
				temp += Y[i][j] - P[i][j];
			}
                   res[i]=temp/(category-1);     
		}

            return res;
        }
       
            public double [][] avgResidual2D(){
        double [] res = avgResidual1D();
         double[][] res2 = new double[res.length][1];
	for(int i = 0; i < res2.length; i++) {
	    res2[i][0] = res[i];
	}
	return res2;
        }
   //xwsun     
	public double[][] getWMatrix() {
		double[][] W = new double[Y.length * (category - 1)][Y.length * (category - 1)];
		for (int i = 0; i < category - 1; i++) {
			for (int j = 0; j < category - 1; j++) {
				for (int k = 0; k < P.length; k++) {
					W[i * P.length + k][j * P.length + k] = i == j ? P[k][i] * (1 - P[k][j]) : P[k][i] * P[k][j] * (-1);
				}
			}
		}
		return W;
	}

	public boolean stop(RealMatrix b_old, RealMatrix b_new) {
		boolean stopit = true;
		for (int i = 0; i < b_old.getRowDimension(); i++) {
			if (Math.abs(b_old.getEntry(i, 0) - b_new.getEntry(i, 0)) > threshold) {
				stopit = false;
				break;
			}
		}
		return stopit;
	}

	public double Likelihood() {
		double L = 0;
		for (int i = 0; i < P.length; i++) {
			double sum_y = 0;
			double sum_p = 0;
			for (int j = 0; j < P[i].length; j++) {
				L += Y[i][j] * Math.log10(P[i][j]);
				sum_y += Y[i][j];
				sum_p += P[i][j];
			}
			L += (1-sum_y) * Math.log10(1-sum_p);
		}
		return L * (-1);
	}

	public static void main(String[] args) {
		double[] Y = { 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 };
		double[][] X = { { 1, 15, 4 }, { 1, 30, 14 }, { 1, 31, 16 }, { 1, 31, 11 }, { 1, 32, 17 }, { 1, 29, 10 },
				{ 1, 30, 8 }, { 1, 31, 12 }, { 1, 32, 6 }, { 1, 40, 7 } };
		double[] b = { 0, 0, 0 };
		MultiClassLogisticRegression MLogReg = new MultiClassLogisticRegression(Y, X, 2);
		MLogReg.MLE();
	}
}
