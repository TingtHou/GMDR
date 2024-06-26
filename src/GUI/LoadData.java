package GUI;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import gmdr.Main;

public class LoadData extends JFrame implements ItemListener,ActionListener,ChangeListener
{
	private boolean is_fast_selected=false;
	private boolean UsePhe=false;
	private JTabbedPane tabpanel=new JTabbedPane(JTabbedPane.NORTH);
	private String name_ped;
	private String name_map;
	
	private JPanel pnlbtnokandcancel=new JPanel();
	private JButton buttonYes=new JButton("   OK   ");
	private JButton buttonNo=new JButton ("Cancel");
			
	private JPanel BinaryInput =new JPanel();
	
	private JPanel pnlbinQuickFiles=new JPanel();
	private JCheckBox ckuseqickboxbin=new JCheckBox("");
	private JComboBox<String> cobbinfilelist=new JComboBox<String>();
	private String fast_name1[];
	
	private JPanel set1=new JPanel();
	private JPanel pnlbedfilechoose=new JPanel();
	private JLabel labelbed=new JLabel(".bed file");
	private JTextArea txabedfilepath=new JTextArea(1,26);
	private JButton btnbedbrowse=new JButton("Browse");
	
	private JPanel pnlbimfilechoose=new JPanel();
	private JLabel labelbim=new JLabel(".bim file");
	private JTextArea txabinfilepath=new JTextArea(1,26);
	private JButton btnbinbrowse=new JButton("Browse");
	
	private JPanel pnlfamfilechoose=new JPanel();
	private JLabel labelfam=new JLabel(".fam file");
	private JTextArea txafamfilepath=new JTextArea(1,26);
	private JButton btnfambrowse=new JButton("Browse");
	
	
	
	private JPanel StandardInput=new JPanel();
	
	private JPanel pnlstandardQuickFiles=new JPanel();
	private JCheckBox ckusequickboxstandard=new JCheckBox("");
	private JComboBox<String> cbostandardfilelist=new JComboBox<String>();
	private String fast_name2[];
	
	private JPanel set2=new JPanel();
	private JPanel pnlstandardpedfilechoose=new JPanel();
	private JLabel labelped=new JLabel(".ped file");
	private JTextArea txapedfilepath=new JTextArea(1,26);
	private JButton btnpedbrowse=new JButton("Browse");
	
	private JPanel pnlstandardmapfilechoose=new JPanel();
	private JLabel labelmap=new JLabel(".map file");
	private JTextArea txamapfilepath=new JTextArea(1,26);
	private JButton btnmapbrowse=new JButton("Browse");
	
	
	
	private JPanel AlternatePhenotype=new JPanel();
	
	private JPanel pnlpheno=new JPanel();
	private JCheckBox ckusepheno=new JCheckBox("");
	private JLabel labelpheno=new JLabel("use alternative phenotype");
	private JTextArea txaphenofilepath=new JTextArea(1,15);
	private JButton btnphenobrowse=new JButton("Browse");
	private final JPanel pnlbinfilechoose = new JPanel();
	private final JPanel pnlstandardfilechoose = new JPanel();
	private boolean binary=true;
	public static void main(String[] args) {
		LoadData loadData=new LoadData();
		loadData.setVisible(true);
	}
	
	
	public LoadData()
	{
		setTitle("Input Data");
		if(GUIMDR.name_file!=null)
		{
			fast_name1=new String[GUIMDR.name_file.length];
			for(int i=0;i<fast_name1.length;i++)
			{
				fast_name1[i]=new String(GUIMDR.name_file[i]);
			}
			fast_name2=new String[GUIMDR.name_file.length];
			for(int i=0;i<fast_name2.length;i++)
			{
				fast_name2[i]=new String(GUIMDR.name_file[i]);
			}
		}
		else
		{
			fast_name1=new String[0];
			fast_name2=new String[0];
		}
		
		tabpanel.add("Binary Input",BinaryInput);
		tabpanel.add("Standard Input",StandardInput);
		tabpanel.add("Alternate Phenotype",AlternatePhenotype);
		tabpanel.addChangeListener(this);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(tabpanel);
		getContentPane().add(pnlbtnokandcancel, BorderLayout.SOUTH);
		Iterator<Entry<String, String>> it = GUIMDR.gmdrini.entrySet().iterator();
        while (it.hasNext()) 
        {  
        	
            Map.Entry<String, String> entry =  it.next();  
            String key = entry.getKey();  
            String value = entry.getValue();  
			if (key.equals("bed"))
            {
				txabedfilepath.setText(value);
				
			}
			if (key.equals("bim"))
            {
				txabinfilepath.setText(value);
				
			}
			if (key.equals("fam"))
            {
				txafamfilepath.setText(value);
				
			}
			if (key.equals("ped"))
            {
				txapedfilepath.setText(value);
				
			}
			if (key.equals("map"))
            {
				txamapfilepath.setText(value);
				
			}
			if (key.equals("phe"))
            {
				txaphenofilepath.setText(value);
				
			}
        }

		initBinary();
		initStandardInput();
		initAleternatePhenotype();
		initLast();
		
		this.setSize(649,422);
		this.setVisible(true);
		this.setLocation(470,210);
		GUIMDR.myUI.DataMenuItem[1].setEnabled(true);
	}
	
	public void initBinary()
	{
		for(int i=0;i<fast_name1.length;i++)
		{
			cobbinfilelist.addItem(fast_name1[i]);
		}
		pnlbinQuickFiles.add(ckuseqickboxbin);
		ckuseqickboxbin.addItemListener(this);
		BinaryInput.setLayout(new BorderLayout(0, 0));
		pnlbinQuickFiles.setBorder(BorderFactory.createTitledBorder("QuickFileset"));
		pnlbinQuickFiles.add(cobbinfilelist);
		cobbinfilelist.setEnabled(false);
		BinaryInput.add(pnlbinQuickFiles, BorderLayout.NORTH);
		
		BinaryInput.add(pnlbinfilechoose, BorderLayout.CENTER);
		pnlbinfilechoose.setLayout(new GridLayout(0, 1, 0, 0));
		pnlbinfilechoose.add(pnlbedfilechoose);
		pnlbedfilechoose.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		labelbed.setVerticalAlignment(SwingConstants.TOP);
		labelbed.setHorizontalAlignment(SwingConstants.LEFT);
		pnlbedfilechoose.add(labelbed);
		pnlbedfilechoose.add(txabedfilepath);
		pnlbedfilechoose.add(btnbedbrowse);
		pnlbinfilechoose.add(pnlbimfilechoose);
		
		
		pnlbimfilechoose.add(labelbim);
		pnlbimfilechoose.add(txabinfilepath);
		pnlbimfilechoose.add(btnbinbrowse);
		pnlbinfilechoose.add(pnlfamfilechoose);
		
		
		pnlfamfilechoose.add(labelfam);
		pnlfamfilechoose.add(txafamfilepath);
		pnlfamfilechoose.add(btnfambrowse);
		btnfambrowse.addActionListener(this);
		btnbinbrowse.addActionListener(this);
		btnbedbrowse.addActionListener(this);
	//	BinaryInput.setLayout(new BoxLayout(BinaryInput,BoxLayout.Y_AXIS));
	}
	
	void initStandardInput()
	{
		pnlstandardQuickFiles.add(ckusequickboxstandard);
		ckusequickboxstandard.setSelected(false);
		ckusequickboxstandard.addItemListener(this);
		pnlstandardQuickFiles.setBorder(BorderFactory.createTitledBorder("QuickFileset"));
		pnlstandardQuickFiles.add(cbostandardfilelist);
		cbostandardfilelist.setEnabled(false);
		for(int i=0;i<fast_name2.length;i++)
		{
			cbostandardfilelist.addItem(fast_name2[i]);
		}
		StandardInput.setLayout(new BorderLayout(0, 0));
		
		
	//	set2.add(set21);
	//	set2.add(set22);
	//	set2.setLayout(new GridLayout(1,1,0,0));
		
		StandardInput.add(pnlstandardQuickFiles, BorderLayout.NORTH);
		
		StandardInput.add(pnlstandardfilechoose, BorderLayout.CENTER);
		pnlstandardfilechoose.setLayout(new GridLayout(0, 1, 0, 0));
		pnlstandardfilechoose.add(pnlstandardpedfilechoose);
		
		pnlstandardpedfilechoose.add(labelped);
		pnlstandardpedfilechoose.add(txapedfilepath);
		pnlstandardpedfilechoose.add(btnpedbrowse);
		pnlstandardfilechoose.add(pnlstandardmapfilechoose);
		
		pnlstandardmapfilechoose.add(labelmap);
		pnlstandardmapfilechoose.add(txamapfilepath);
		pnlstandardmapfilechoose.add(btnmapbrowse);	
		btnmapbrowse.addActionListener(this);
		btnpedbrowse.addActionListener(this);
	}

	public void initAleternatePhenotype()
	{
		ckusepheno.addItemListener(this);
		pnlpheno.add(ckusepheno);
		pnlpheno.add(labelpheno);
		pnlpheno.add(txaphenofilepath);
		btnphenobrowse.addActionListener(this);
		pnlpheno.add(btnphenobrowse);
		
		AlternatePhenotype.add(pnlpheno);
		btnphenobrowse.setEnabled(false);
		txaphenofilepath.setEnabled(false);
		AlternatePhenotype.setLayout(new GridLayout(1, 1, 0, 0));
		//AlternatePhenotype.setLayout(new BoxLayout(AlternatePhenotype, BoxLayout.X_AXIS));
	}
	
	public void initLast()
	{
		pnlbtnokandcancel.add(buttonYes);
		buttonYes.addActionListener(this);
		pnlbtnokandcancel.add(buttonNo);
		buttonNo.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		JButton temp_button;
		temp_button=(JButton)e.getSource();
		if (temp_button==btnbedbrowse)
		{
			JFileChooser chooser = new JFileChooser(GUIMDR.project_path);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("BED","bed");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(new JPanel()); 
			if (returnVal!=JFileChooser.APPROVE_OPTION) {
				return;
			}
			txabedfilepath.setText(chooser.getSelectedFile().getAbsolutePath());
		
			
		
		}
		if (temp_button==btnbinbrowse)
		{
			JFileChooser chooser = new JFileChooser(GUIMDR.project_path);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("BIM","bim");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(new JPanel()); 
			if (returnVal!=JFileChooser.APPROVE_OPTION) {
				return;
			}
			txabinfilepath.setText(chooser.getSelectedFile().getAbsolutePath());
		}
		if (temp_button==btnfambrowse)
		{
			JFileChooser chooser = new JFileChooser(GUIMDR.project_path);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("FAM File","fam");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(new JPanel()); 
			if (returnVal!=JFileChooser.APPROVE_OPTION) {
				return;
			}
			txafamfilepath.setText(chooser.getSelectedFile().getAbsolutePath());
		}
		if(temp_button==btnpedbrowse)
		{
			JFileChooser chooser=new JFileChooser(GUIMDR.project_path);
			chooser.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter=new FileNameExtensionFilter("ped File","ped");
			chooser.setFileFilter(filter);
			int returnVal =chooser.showOpenDialog(new JPanel());
			if (returnVal!=JFileChooser.APPROVE_OPTION) {
				return;
			}
			chooser.getSelectedFile().getName();
			txapedfilepath.setText(chooser.getSelectedFile().getAbsolutePath());
			if(!txapedfilepath.getText().isEmpty() && returnVal==chooser.APPROVE_OPTION)
			{
				labelmap.setEnabled(true);
				txamapfilepath.setEnabled(true);
				btnmapbrowse.setEnabled(true);
			}
		}
		if(temp_button==btnmapbrowse)
		{
			JFileChooser chooser=new JFileChooser(GUIMDR.project_path);
			FileNameExtensionFilter filter=new FileNameExtensionFilter("map File","map");
			chooser.setFileFilter(filter);
			int returnVal =chooser.showOpenDialog(new JPanel());
			if (returnVal!=JFileChooser.APPROVE_OPTION) {
				return;
			}
			txamapfilepath.setText(chooser.getSelectedFile().getAbsolutePath());
			name_map=new String(chooser.getSelectedFile().getAbsolutePath());
		}
		
       if (temp_button==btnphenobrowse) 
       {
			
			JFileChooser pheChooser=new JFileChooser(GUIMDR.project_path);
			FileNameExtensionFilter filter=new FileNameExtensionFilter("Phenotype File", "phe");
			pheChooser.setFileFilter(filter);
			int returnVal=pheChooser.showOpenDialog(new JPanel());
			if (returnVal!=JFileChooser.APPROVE_OPTION) {
				return;
			}
			txaphenofilepath.setText(pheChooser.getSelectedFile().getAbsolutePath());
			
			
		}
		
		if(temp_button==buttonYes)
		{
			String temp_name;
			File temp_file;
			GUIMDR.name_bed=new File("");
			GUIMDR.name_bim=new File("");	
			GUIMDR.name_fam=new File("");
			
			GUIMDR.name_ped=new File("");
			GUIMDR.name_map=new File("");
			GUIMDR.name_phe=new File("");
			GUIMDR.open=0;
			if(binary)
			{
				if (GUIMDR.gmdrini.containsKey("ped")||GUIMDR.gmdrini.containsKey("map")) 
				{
					try
					{
					
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tRemoving old standard data files from project \n", GUIMDR.myUI.keyWordwarning);
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(),"\t\tRemoving "+GUIMDR.gmdrini.get("ped")+".\t",GUIMDR.myUI.keyWordwarning);
						GUIMDR.gmdrini.remove("ped");
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(),"Successed\n",GUIMDR.myUI.keyWordsuccessed);
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(),"\t\tRemoving "+GUIMDR.gmdrini.get("map")+".\t",GUIMDR.myUI.keyWordwarning);
						GUIMDR.gmdrini.remove("map");
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(),"Successed\n",GUIMDR.myUI.keyWordsuccessed);						
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if(is_fast_selected)
				{
					temp_name=new String(cobbinfilelist.getSelectedItem().toString());
					temp_name=temp_name.substring(0,temp_name.lastIndexOf("."));
					
					temp_file=new File(GUIMDR.project_path+"//"+temp_name+".bed");
					
					
					if(!temp_file.exists())
					{
						JOptionPane.showMessageDialog(null,"Can't find a BED file");
						return;
					}
					GUIMDR.name_bed=new File(temp_file.getAbsolutePath());
					if (GUIMDR.gmdrini.containsKey("bed")&&!GUIMDR.gmdrini.get("bed").equals(temp_file.getAbsolutePath())) 
					{
						try
						{
							GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tRemoving old bed file "+GUIMDR.gmdrini.get("bed")+" from project successed\n", GUIMDR.myUI.keyWordwarning);
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					GUIMDR.gmdrini.put("bed", temp_file.getAbsolutePath());
					try 
					{
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tLoading bed file "+temp_file.getAbsolutePath()+"\tSuccessed\n", GUIMDR.myUI.keyWordsuccessed);
					} catch (BadLocationException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
						
					
					temp_file=new File(GUIMDR.project_path+"//"+temp_name+".bim");
					if(!temp_file.exists())
					{
						JOptionPane.showMessageDialog(null,"Can't find a BIM file");
						return;
					}
					GUIMDR.name_bim=new File(temp_file.getAbsolutePath());
					if (GUIMDR.gmdrini.containsKey("bim")&&!GUIMDR.gmdrini.get("bim").equals(temp_file.getAbsolutePath())) {
						try {
							GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tRemoving old bim file "+GUIMDR.gmdrini.get("bim")+" from project successed\n", GUIMDR.myUI.keyWordwarning);
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					GUIMDR.gmdrini.put("bim", temp_file.getAbsolutePath());
					try {
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tLoading bim file "+temp_file.getAbsolutePath()+"\tSuccessed\n", GUIMDR.myUI.keyWordsuccessed);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					temp_file=new File(GUIMDR.project_path+"//"+temp_name+".fam");
					if(!temp_file.exists())
					{
						JOptionPane.showMessageDialog(null,"Can't find a FAM file");
						return;
					}
					
					GUIMDR.name_fam=new File(temp_file.getAbsolutePath());
					if (GUIMDR.gmdrini.containsKey("fam")&&!GUIMDR.gmdrini.get("fam").equals(temp_file.getAbsolutePath())) {
							try {
								GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tRemoving old fam file "+GUIMDR.gmdrini.get("fam")+" from project successed\n", GUIMDR.myUI.keyWordwarning);
							} catch (BadLocationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					GUIMDR.gmdrini.put("fam", temp_file.getAbsolutePath());
					try {
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tLoading fam file "+temp_file.getAbsolutePath()+"\tSuccessed\n", GUIMDR.myUI.keyWordsuccessed);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					this.dispose();
				}
				else
				{
					GUIMDR.name_bed=new File(txabedfilepath.getText());
					if(!GUIMDR.name_bed.exists())
					{
						JOptionPane.showMessageDialog(null,"Can't find a BED file");
						return;
					}						
					if (GUIMDR.gmdrini.containsKey("bed")&&!GUIMDR.gmdrini.get("bed").equals(GUIMDR.name_bed.getAbsolutePath())) {
						try {
							GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tRemoving old bed file "+GUIMDR.gmdrini.get("bed")+" from project successed\n", GUIMDR.myUI.keyWordwarning);
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					GUIMDR.gmdrini.put("bed", GUIMDR.name_bed.getAbsolutePath());
					try {
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tLoading bed file "+txabedfilepath.getText()+"\tSuccessed\n", GUIMDR.myUI.keyWordsuccessed);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					GUIMDR.name_bim=new File(txabinfilepath.getText());
					if(!GUIMDR.name_bim.exists())
					{
						JOptionPane.showMessageDialog(null,"Can't find a BIM file");
						return;
					}
					if (GUIMDR.gmdrini.containsKey("bim")&&!GUIMDR.gmdrini.get("bim").equals(GUIMDR.name_bim.getAbsolutePath())) {
						try {
							GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tRemoving old bim file "+GUIMDR.gmdrini.get("bim")+" from project successed\n", GUIMDR.myUI.keyWordwarning);
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					GUIMDR.gmdrini.put("bim", GUIMDR.name_bim.getAbsolutePath());
					try {
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tLoading bim file "+txabinfilepath.getText()+"\tSuccessed\n", GUIMDR.myUI.keyWordsuccessed);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					GUIMDR.name_fam=new File(txafamfilepath.getText());
					if(!GUIMDR.name_fam.exists())
					{
						JOptionPane.showMessageDialog(null,"Can't find a FAM file");
						return;
					}
					if (GUIMDR.gmdrini.containsKey("fam")&&!GUIMDR.gmdrini.get("fam").equals(GUIMDR.name_fam.getAbsolutePath()))
					{
						
						if (GUIMDR.gmdrini.containsKey("fam")) {
							try {
								GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tRemoving old fam file "+GUIMDR.gmdrini.get("fam")+" from project successed\n", GUIMDR.myUI.keyWordwarning);
							} catch (BadLocationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					GUIMDR.gmdrini.put("fam", 	GUIMDR.name_fam.getAbsolutePath());
					try {
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tLoading fam file "+txafamfilepath.getText()+"\tSuccessed\n", GUIMDR.myUI.keyWordsuccessed);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		
					this.dispose();
				}
				
			}
			else
			{
				if (GUIMDR.gmdrini.containsKey("bed")||GUIMDR.gmdrini.containsKey("bim")||GUIMDR.gmdrini.containsKey("fam")) 
				{
					try
					{
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tRemoving old binary data files from project \n", GUIMDR.myUI.keyWordwarning);
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(),"\t\tRemoving "+GUIMDR.gmdrini.get("bed")+".\t",GUIMDR.myUI.keyWordwarning);
						GUIMDR.gmdrini.remove("bed");
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(),"Successed\n",GUIMDR.myUI.keyWordsuccessed);
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(),"\t\tRemoving "+GUIMDR.gmdrini.get("fam")+".\t",GUIMDR.myUI.keyWordwarning);
						GUIMDR.gmdrini.remove("fam");
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(),"Successed\n",GUIMDR.myUI.keyWordsuccessed);
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(),"\t\tRemoving "+GUIMDR.gmdrini.get("bim")+".\t",GUIMDR.myUI.keyWordwarning);
						GUIMDR.gmdrini.remove("bim");
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(),"Successed\n",GUIMDR.myUI.keyWordsuccessed);
						
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if(is_fast_selected)
				{
					temp_name=new String(cbostandardfilelist.getSelectedItem().toString());
					temp_name=temp_name.substring(0,temp_name.lastIndexOf("."));
				
					temp_file=new File(GUIMDR.project_path+"//"+temp_name+".ped");
				//	System.out.print(temp_file);
					if(!temp_file.exists())
					{
						JOptionPane.showMessageDialog(null,"Can't find a PED file");
						return;
					}
					GUIMDR.name_ped=new File(temp_file.getAbsolutePath());
					if (GUIMDR.gmdrini.containsKey("ped")&&!GUIMDR.gmdrini.get("ped").equals(GUIMDR.name_ped.getAbsolutePath())) 
					{	
						
						try {
								GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tRemoving old ped file "+GUIMDR.gmdrini.get("ped")+" from project successed\n", GUIMDR.myUI.keyWordwarning);
							} catch (BadLocationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}
					GUIMDR.gmdrini.put("ped", temp_file.getAbsolutePath());
					try {
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tLoading ped file "+temp_file.getAbsolutePath()+"\tSuccessed\n", GUIMDR.myUI.keyWordsuccessed);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}		
					
				
					temp_file=new File(GUIMDR.project_path+"//"+temp_name+".map");
					if(!temp_file.exists())
					{
						JOptionPane.showMessageDialog(null,"Can't find a MAP file");
						return;
					}
					GUIMDR.name_map=new File(temp_file.getAbsolutePath());
					if (GUIMDR.gmdrini.containsKey("map")&&!GUIMDR.gmdrini.get("map").equals(GUIMDR.name_map.getAbsolutePath())) 
					{	
						
						try {
								GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tRemoving old map file "+GUIMDR.gmdrini.get("map")+" from project successed\n", GUIMDR.myUI.keyWordwarning);
							} catch (BadLocationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}	
					GUIMDR.gmdrini.put("map", temp_file.getAbsolutePath());
					try {
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tLoading map file "+temp_file.getAbsolutePath()+"\tSuccessed\n", GUIMDR.myUI.keyWordsuccessed);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}		
				
				}
				else
				{
					temp_file=new File(txapedfilepath.getText());
					if(!temp_file.exists())
					{
						JOptionPane.showMessageDialog(null,"Can't find a PED file");
						return;
					}
					GUIMDR.name_ped=temp_file;
					
					if (GUIMDR.gmdrini.containsKey("ped")&&!GUIMDR.gmdrini.get("ped").equals(GUIMDR.name_ped.getAbsolutePath())) 
					{	
						
						try {
								GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tRemoving old ped file "+GUIMDR.gmdrini.get("ped")+" from project successed\n", GUIMDR.myUI.keyWordwarning);
							} catch (BadLocationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}	
					GUIMDR.gmdrini.put("ped", temp_file.getAbsolutePath());
					try {
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tLoading ped file "+temp_file.getAbsolutePath()+"\tSuccessed\n", GUIMDR.myUI.keyWordsuccessed);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}		
					temp_file=new File(txamapfilepath.getText());
					if(!temp_file.exists())
					{
						JOptionPane.showMessageDialog(null,"Can't find a map file");
						return;
					}
					GUIMDR.name_map=temp_file;
					
					if (GUIMDR.gmdrini.containsKey("map")&&!GUIMDR.gmdrini.get("map").equals(GUIMDR.name_map.getAbsolutePath())) 
					{	
						
						try {
								GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tRemoving old map file "+GUIMDR.gmdrini.get("map")+" from project successed\n", GUIMDR.myUI.keyWordwarning);
							} catch (BadLocationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}	
					GUIMDR.gmdrini.put("map", temp_file.getAbsolutePath());
					try {
						GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tLoading map file "+temp_file.getAbsolutePath()+"\tSuccessed\n", GUIMDR.myUI.keyWordsuccessed);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}		
					
					
				}
				//SimpleTheData thedata=new SimpleTheData(GMDR.name_ped.getAbsolutePath(),GMDR.name_map.getAbsolutePath());
				this.dispose();
			}
			
			if (UsePhe) {
				
				Analysis.phenofile=new File(txaphenofilepath.getText());
				if(!Analysis.phenofile.exists())
				{
					JOptionPane.showMessageDialog(null,"Can't find a phenotype file");
					return;
				}
				if (GUIMDR.gmdrini.containsKey("phe")&&!GUIMDR.gmdrini.get("phe").equals(Analysis.phenofile.getAbsolutePath())) 
				{	
					
					try {
							GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tRemoving old phenotype file "+GUIMDR.gmdrini.get("phe")+" from project successed\n", GUIMDR.myUI.keyWordwarning);
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}	
				GUIMDR.gmdrini.put("phe", Analysis.phenofile.getAbsolutePath());
				try {
					GUIMDR.myUI.doc.insertString(GUIMDR.myUI.doc.getLength(), Main.dateFormat.format(Main.date.getTime())+"\tLoading phenotype file "+Analysis.phenofile.getAbsolutePath()+"\tSuccessed\n", GUIMDR.myUI.keyWordsuccessed);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}					
				Analysis.txtphenopath.setText(txaphenofilepath.getText());
				Analysis.refresh();
			}
			
			GUIMDR.myUI.DataMenuItem[1].setEnabled(true);
			GUIMDR.myUI.ToolsMenuItem[0].setEnabled(true);;
		}
		if(temp_button==buttonNo)
		{
			this.dispose();
		}
		
		
	}

	public void itemStateChanged(ItemEvent e) 
	{
		JCheckBox temp_check=(JCheckBox)e.getSource();
	
		if(temp_check==ckuseqickboxbin)
		{
			if(ckuseqickboxbin.isSelected())
			{
				cobbinfilelist.setEnabled(true);
				
				labelbed.setEnabled(false);
				txabedfilepath.setEnabled(false);
				btnbedbrowse.setEnabled(false);
				
				labelbim.setEnabled(false);
				txabinfilepath.setEnabled(false);
				btnbinbrowse.setEnabled(false);
				
				labelfam.setEnabled(false);
				txafamfilepath.setEnabled(false);
				btnfambrowse.setEnabled(false);
				
				is_fast_selected=true;
			}
			else
			{
				cobbinfilelist.setEnabled(false);
				
				labelbed.setEnabled(true);
				txabedfilepath.setEnabled(true);
				btnbedbrowse.setEnabled(true);
				
				labelbim.setEnabled(true);
				txabinfilepath.setEnabled(true);
				btnbinbrowse.setEnabled(true);
				
				labelfam.setEnabled(true);
				txafamfilepath.setEnabled(true);
				btnfambrowse.setEnabled(true);
				
				is_fast_selected=false;
			}
			return;
		}
		if(temp_check==ckusequickboxstandard)
		{
			if(ckusequickboxstandard.isSelected())
			{
				cbostandardfilelist.setEnabled(true);
				
				labelped.setEnabled(false);
				txapedfilepath.setEnabled(false);
				btnpedbrowse.setEnabled(false);
			
				labelmap.setEnabled(false);
				txamapfilepath.setEnabled(false);
				btnmapbrowse.setEnabled(false);
				
				is_fast_selected=true;
			}
			else
			{
				cbostandardfilelist.setEnabled(false);
				
				labelped.setEnabled(true);
				txapedfilepath.setEnabled(true);
				btnpedbrowse.setEnabled(true);
			
				labelmap.setEnabled(true);
				txamapfilepath.setEnabled(true);
				btnmapbrowse.setEnabled(true);
				
				is_fast_selected=false;
			}
		}
		if (temp_check==ckusepheno) 
		{
			UsePhe=true;
			btnphenobrowse.setEnabled(true);
			txaphenofilepath.setEnabled(true);
		}
	}

	public void stateChanged(ChangeEvent e) 
	{
		int index=tabpanel.getSelectedIndex();
		switch(index)
		{
		case 0:
			binary=true;
		//	GUIMDR.standard=false;
			break;
		case 1:
			//GUIMDR.standard=true;
			binary=false;
			break;
		case 2:
			break;
		default:
			break;
		}
	}
}


