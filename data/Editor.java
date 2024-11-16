import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

/***
 * ѧ����ʿ���޸��� 
 */
public class Editor {
	
	/*��Ÿ�����Ʒ���ƣ���������������ɶ���֮ǰ��ʼ��*/
	private static List<String> articlesList = new ArrayList<String>();
	
	static {
		articlesListInit();
	} 
	
	/*��Ÿ��ֲ����׵�ַ*/
	HashMap<String, Integer> addressMap = new HashMap<String, Integer>();
	
	/*��Ÿ�����Ʒ������*/
	HashMap<String, Integer> articlesMap = new HashMap<String, Integer>();
	
	/*�浵�ļ�������������·��*/
	String archiveFilename=null;
	
	/*ѡ��Ľ�ɫ��ţ���0��ʼ*/
	int roleSelectedIndex=-1;
	
	/*�ļ����͹�����*/
	ExtensionFileFilter fileFilter = new ExtensionFileFilter();
	
	/*����壬����BorderLayout����*/
	JFrame mainFrame = new JFrame("ѧ����ʿ�Ŵ浵�޸���");
		
	/*northPanel����mainFrame��NORTH������openFilePanel��moneyPanel*/
	JPanel northPanel = new JPanel();
	
	/*openFilePanel����openButton��readButton��filenameText*/
	JPanel openFilePanel = new JPanel();
	JFileChooser fileChooser = new JFileChooser(".");
	JButton openButton = new JButton("���");
	JButton readButton = new JButton("��ȡ");
	JTextField filenameText = new JTextField(30);
	
	/*moneyPanel����moneyText��moneyLabel*/
	JPanel moneyPanel = new JPanel();
	JTextField moneyText = new JTextField(35);
	JLabel moneyLabel = new JLabel("��Ǯ");
	
	/*rolePanel����mainFrame��WEST������roleScrollPane*/
	JPanel rolesPanel = new JPanel();
	JList rolesList = new JList(new String[]{" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "});
	JScrollPane roleScrollPane = new JScrollPane(rolesList);
	
	/*attributesPanel����mainFrame��CENTER*/
	JPanel attributesPanel = new JPanel();
	private Box attributesVerticalBox = Box.createVerticalBox();
	private Box hpHorizontalBox = Box.createHorizontalBox();
	JLabel hpLabel = new JLabel("��  ��  ֵ    ");
	JTextField hp = new JTextField(5);//HP
	private Box mpHorizontalBox = Box.createHorizontalBox();
	JLabel mpLabel = new JLabel("ħ  ��  ֵ    ");
	JTextField mp = new JTextField(5);//MP
	private Box magicPowerHorizontalBox = Box.createHorizontalBox();
	JLabel magLabel = new JLabel("ħ        ��    ");
	JTextField mag = new JTextField(5);//MAG
	private Box strengthHorizontalBox = Box.createHorizontalBox();
	JLabel attLabel = new JLabel("��        ��    ");
	JTextField att = new JTextField(5);//ATT
	private Box physiqueHorizontalBox = Box.createHorizontalBox();
	JLabel defLabel = new JLabel("��        ��    ");
	JTextField def = new JTextField(5);//DEF
	private Box agilityHorizontalBox = Box.createHorizontalBox();
	JLabel spdLabel = new JLabel("��        ��    ");
	JTextField spd = new JTextField(5);//SPD
	private Box luckHorizontalBox = Box.createHorizontalBox();
	JLabel luckLabel = new JLabel("��  ��  ��    ");
	JTextField luck = new JTextField(5);//���˶�
	private Box swordHorizontalBox = Box.createHorizontalBox();
	JLabel swordLabel = new JLabel("��  ��  ��    ");
	JTextField sword = new JTextField(5);//�̱���
	private Box pikeHorizontalBox = Box.createHorizontalBox();
	JLabel pikeLabel = new JLabel("��  ��  ��    ");
	JTextField pike = new JTextField(5);//������
	private Box bowHorizontalBox = Box.createHorizontalBox();
	JLabel bowLabel = new JLabel("��        ��    ");
	JTextField bow = new JTextField(5);//����
	private Box integrationHorizontalBox = Box.createHorizontalBox();
	JLabel integrationLabel = new JLabel("��  ��  ��    ");
	JTextField integration = new JTextField(5);//�ں���
	private Box eloquenceHorizontalBox = Box.createHorizontalBox();
	JLabel eloquenceLabel = new JLabel("��        ��    ");
	JTextField eloquence = new JTextField(5);//�ڲ�
	private Box experienceHorizontalBox = Box.createHorizontalBox();
	JLabel experienceLabel = new JLabel("��  ��  ֵ    ");
	JTextField experience = new JTextField(5);//����ֵ
	private Box allMagicHorizontalBox = Box.createHorizontalBox();	
	JButton setAllMagicButton = new JButton("ħ   ��   ǿ   ��");
	private Box swordLearningHorizontalBox = Box.createHorizontalBox();
	JLabel swordLearningLabel = new JLabel("�̱��ɳ�    ");
	JTextField swordLearning = new JTextField(5);//�̳ɳ�
	private Box pikeLearningHorizontalBox = Box.createHorizontalBox();
	JLabel pikeLearningLabel = new JLabel("�����ɳ�    ");
	JTextField pikeLearning = new JTextField(5);//���ɳ�
	private Box bowLearningHorizontalBox = Box.createHorizontalBox();
	JLabel bowLearningLabel = new JLabel("�����ɳ�    ");
	JTextField bowLearning = new JTextField(5);//���ɳ�
	private Box magicLearningHorizontalBox = Box.createHorizontalBox();
	JLabel magicLearningLabel = new JLabel("ħ���ɳ�    ");
	JTextField magicLearning = new JTextField(5);//ħ�ɳ�
	
	/*articlesPanel����mainFrame��EAST������equipmentsPanel��itemsPanel*/
	JPanel articlesPanel = new JPanel();
	JPanel equipmentsPanel = new JPanel();
	private Box equipmentsVerticalBox = Box.createVerticalBox();
	private Box helmetHorizontalBox = Box.createHorizontalBox();
	JLabel helmetLabel = new JLabel("ͷ    ��    ");//index+83
	JComboBox helmet = new JComboBox(new String[]{"<��>","��ľ��","����ͷ��","ͭ��","������","��ñ","�����Ƥ��","�������","ԡѪ���","ҹɫ֮��","�۷�֮��","˫��֮��","��裿�","�����","����֮��","�ƽ����","�����","ˮ����","�λ�֮��","����֮��","ʥ��֮��"});//ͷ��
	private Box clothesHorizontalBox = Box.createHorizontalBox();
	JLabel clothesLabel = new JLabel("��    ��    ");//index+103
	JComboBox clothes = new JComboBox(new String[]{"<��>","�Ʒ�","��ľ��","��ľ��","ӲƤ��","����","�����","���֮��","ԡѪ����","ħ��֮��","��ʿ����","�ضܼ�","�������","��������","��������","��ʥ��","֩��ħ��","ħ������","��ɫ֮��","���Ϻڼ�","������֮��"});//�·�
	private Box weaponHorizontalBox = Box.createHorizontalBox();
	JLabel weaponLabel = new JLabel("��    ��    ");//index+0
	JComboBox weapon = new JComboBox(new String[]{"<��>","ľ��","����","�̸�","˫�г���","�����䵶","���ս�","���߱���","�ǻ���","������","����֮��","�����䵶","���鱦��","��ͷ��","���佣","������","����֮��","���㳤�̽�","��ʹ��","��ʿ��","�����䵶","����","���Ǳ���","ն����","����֮��","�λ���","�¿���֮��","ľǹ","����","����","��ǹ","��ǹ","�ӳ���������","�������","���鳤��","����ǹ","����׶","��Ӱǹ","��֬�����","�ڰ���ì","������ʹ��","����ǹ","��ŭ�ͻ�ǹ","����ǹ","����ì","�λ���ǹ","�ƽ���ǹ","ľ��","��̥��","���ٹ�","���ǹ�","������","���ɹ�","��β��","ħ�ƹ�","����","���鹭","ǿ����","ʮ�ֹ�","������","��ʿ��","����ǿ����","������","��צ��","�λ�ʮ�ֹ�","��Ʒ���蹭","ʫ��ǿ��","��ľħ��","�ڰ�ɭ��","˫����","����֮��","�����ض�","ħʯ��","��������","��ɫ���˹","���֮��","ϣ��֮��","�ƽ�֮��","�������","������","�������","�������","�λô��","����","����֮��"});//����
	private Box shoesHorizontalBox = Box.createHorizontalBox();
	JLabel shoesLabel = new JLabel("Ь    ��    ");//index+123
	JComboBox shoes = new JComboBox(new String[]{"<��>","��ѥ","��ľѥ","�����Ƥѥ","����֮ѥ","ɮѥ","¹Ƥ��ѥ","�໢Ƥѥ","����Ƥѥ","���˳�ѥ","̤��ѥ","��ʿѥ","��ʹѥ","����ѥ","����Ƥѥ","����ѥ","ʥ��ѥ","����ѥ","��Ӱ֮ѥ","����ѥ","ѩŮ֮ѥ"});//Ь��
	private Box ornamentHorizontalBox = Box.createHorizontalBox();
	JLabel ornamentLabel = new JLabel("��    Ʒ    ");//index+143
	JComboBox ornament = new JComboBox(new String[]{"<��>","��ħ֮��","��֮��","Ů��֮��","����֮��","�챦ʯ��ָ","����ʯ��ָ","���ʯ��ָ","�����ָ","�߲��ֻ�","�ƽ��ֻ�","�����ֻ�","�ٶ��ֻ�","�����ֻ�","�����ֻ�","����������","��è����","��������","�������","����������","ľ�񻤷�","��֮����","��֮����","��֮����","��֮����","��֮����","��֮����","��֮����","��֮����","ˮ֮����","��֮����","ħ������","ף������","��������","��������","��ʹ����"});//��Ʒ
	
	JPanel itemsPanel = new JPanel();
	private Box itemsVerticalBox = Box.createVerticalBox();
	private Box itemHorizontalBoxA = Box.createHorizontalBox();
	JLabel itemLabelA = new JLabel("��Ʒһ    ");//index+238
	JComboBox itemA = new JComboBox(articlesList.toArray());//��Ʒһ
	private Box itemHorizontalBoxB = Box.createHorizontalBox();
	JLabel itemLabelB = new JLabel("��Ʒ��    ");//index+238
	JComboBox itemB = new JComboBox(articlesList.toArray());//��Ʒ��
	private Box itemHorizontalBoxC = Box.createHorizontalBox();
	JLabel itemLabelC = new JLabel("��Ʒ��    ");//index+238
	JComboBox itemC = new JComboBox(articlesList.toArray());//��Ʒ��
	private Box itemHorizontalBoxD = Box.createHorizontalBox();
	JLabel itemLabelD = new JLabel("��Ʒ��    ");//index+238
	JComboBox itemD = new JComboBox(articlesList.toArray());//��Ʒ��
	private Box itemHorizontalBoxE = Box.createHorizontalBox();
	JLabel itemLabelE = new JLabel("��Ʒ��    ");//index+238
	JComboBox itemE = new JComboBox(articlesList.toArray());//��Ʒ��
	private Box itemHorizontalBoxF = Box.createHorizontalBox();
	JLabel itemLabelF = new JLabel("��Ʒ��    ");//index+238
	JComboBox itemF = new JComboBox(articlesList.toArray());//��Ʒ��
	private Box itemHorizontalBoxG = Box.createHorizontalBox();
	JLabel itemLabelG = new JLabel("��Ʒ��    ");//index+238
	JComboBox itemG = new JComboBox(articlesList.toArray());//��Ʒ��
	private Box itemHorizontalBoxH = Box.createHorizontalBox();
	JLabel itemLabelH = new JLabel("��Ʒ��    ");//index+238
	JComboBox itemH = new JComboBox(articlesList.toArray());//��Ʒ��
	
	
	/*saveButton����j��SOUTH*/
	JButton saveButton = new JButton("��        ��");
	

	
	/**/
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Editor().init();
	}
	
	private void init(){
		addressMapInit();		
		articlesMapInit();
		uiInit();
		
		fileFilter.addExtension("sav");
		fileFilter.setDescription("�浵�ļ�(*.sav)");
		fileChooser.setFileFilter(fileFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		openButton.addActionListener(new ActionListener(){

//			@Override
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showDialog(mainFrame, "�򿪴浵�ļ�");
				if(result==JFileChooser.APPROVE_OPTION){
					archiveFilename = fileChooser.getSelectedFile().getPath();
					filenameText.setText(archiveFilename);
				}
			}
		});
				
		saveButton.addActionListener(new ActionListener(){
//			@Override
			public void actionPerformed(ActionEvent e) {
				setRoleAttributeAndArticles(archiveFilename, roleSelectedIndex);
			}
		});
				
		setAllMagicButton.addActionListener(new ActionListener(){
//			@Override
			public void actionPerformed(ActionEvent e) {
				setAllMagic(archiveFilename, roleSelectedIndex);
			}
		});
		
		readButton.addActionListener(new ActionListener(){
//			@Override
			public void actionPerformed(ActionEvent e) {
				loadArchiveFile(archiveFilename);
			}
		});
		
		rolesList.addListSelectionListener(new ListSelectionListener(){
//			@Override
			public void valueChanged(ListSelectionEvent e) {
				roleSelectedIndex = rolesList.getSelectedIndex();
				if(roleSelectedIndex == -1) roleSelectedIndex = 0;
				getRoleAttributeAndArticles(archiveFilename, roleSelectedIndex);
			}
		});
			
	}
	
	
	
	/*��ȡ�浵�ļ�*/
	private boolean loadArchiveFile(String filename){
		if(filename==null){
			JOptionPane.showMessageDialog(mainFrame, "�浵�ļ���Ϊ��", "�浵�ļ�����", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if("".equals(filename.trim())){
			JOptionPane.showMessageDialog(mainFrame, "�浵�ļ���Ϊ��", "�浵�ļ�����", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		RandomAccessFile saveFile = null;
		try {
				saveFile = new RandomAccessFile(filename,"rw");

				/*��ȡ��Ǯ*/
				saveFile.seek(addressMap.get("money"));
				byte[] moneyAmount = new byte[4];	
				saveFile.read(moneyAmount);
				moneyText.setText(new Integer(changeToIntA(moneyAmount)).toString());
				
				/*��ȡ��ɫ����Ž�ɫ���б�ÿ����ɫ����ռ8���ֽ�*/
				List<String> roleArrayList = new ArrayList<String>();
				roleArrayList.clear();
				byte[] b = new byte[8];
				for(int i=0;;i++){
					saveFile.seek(addressMap.get("roles")+i*addressMap.get("distanceBetweenRoles"));
					saveFile.read(b);
					if(new String(b).length()>6) break;
					roleArrayList.add(new String(b));
				}
				rolesList.setListData(roleArrayList.toArray());
				rolesList.setSelectedIndex(0);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(mainFrame, "�浵�ļ�������", "�浵�ļ�����", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(mainFrame, "��ȡ�浵�ļ�����", "�浵�ļ�����", JOptionPane.ERROR_MESSAGE);
				return false;
			}finally{
				if(saveFile!=null){
					try {
						saveFile.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(mainFrame, "�رմ浵�ļ�������ر��޸�������������", "�浵�ļ�����", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		return true;
	}
	
	
	/*ѧ������ħ��*/
	private boolean setAllMagic(String filename, int roleIndex){
		//200��ħ��������Ʒ����ʼ
		RandomAccessFile saveFile = null;
		if(filename==null){
			JOptionPane.showMessageDialog(mainFrame, "�浵�ļ���Ϊ��", "�浵�ļ�����", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if("".equals(filename.trim())){
			JOptionPane.showMessageDialog(mainFrame, "�浵�ļ���Ϊ��", "�浵�ļ�����", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(roleIndex==-1){
			JOptionPane.showMessageDialog(mainFrame, "���ȡ�浵�ļ���ѡ��һ����ɫ", "��ɫ����", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try{
				saveFile = new RandomAccessFile(filename,"rw");		
				/*����ħ���ľ���ֵΪ0x0300*/
				byte[] b = new byte[2];
				b[0]=0;
				b[1]=3;
				for(int i=0;i<200;i++){
					saveFile.seek(addressMap.get("magic")+roleIndex*addressMap.get("distanceBetweenRoles")+i*2);
					saveFile.write(b);
				}
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(mainFrame, "�浵�ļ�������", "�浵�ļ�����", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(mainFrame, "��ȡ�浵�ļ�����", "�浵�ļ�����", JOptionPane.ERROR_MESSAGE);
				return false;
			}finally{
				if(saveFile!=null){
					try {
						saveFile.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(mainFrame, "�رմ浵�ļ�������ر��޸�������������", "�浵�ļ�����", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		return true;
	}
	
	private void setValueByAddress(RandomAccessFile file, byte[] b, JComponent component, HashMap<String,Integer> addressmap, HashMap<String,Integer> articlesmap,int roleIndex, String attribute, String changeType) throws IOException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		if(file == null || addressmap == null || attribute == null || component == null || changeType == null) return;
		file.seek(addressmap.get(attribute)+roleIndex*addressmap.get("distanceBetweenRoles"));
		Class<?> clazz = component.getClass();
		int attributeValue = 0;
		if(clazz == JTextField.class){
			Method method = component.getClass().getMethod("getText");
			attributeValue = Integer.parseInt((String)method.invoke(component));
		}else if(clazz == JComboBox.class){
			Method method = component.getClass().getMethod("getSelectedItem");
			attributeValue = articlesmap.get(method.invoke(component));
		}
		if("changeToBytesA".equals(changeType)){
			changeToBytesA(attributeValue,b);
		}else if("changeToBytesB".equals(changeType)){
			changeToBytesB(attributeValue,b);
		}
		file.write(b);
	}
	
	private void getValueByAddress(RandomAccessFile file, byte[] b, JComponent component, HashMap<String,Integer> map, int roleIndex, String attribute, String changeType, String equipmentIndex) throws IOException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		if(file == null || map == null || attribute == null || component == null || changeType == null) return;
		file.seek(map.get(attribute)+roleIndex*map.get("distanceBetweenRoles"));	
		file.read(b);
		int attributeValue = 0;
		if("changeToIntA".equals(changeType)){
			attributeValue = new Integer(changeToIntA(b));
		}else if("changeToIntB".equals(changeType)){
			attributeValue = new Integer(changeToIntB(b));
		}
		Class<?> clazz = component.getClass();
		if(clazz == JTextField.class){
			Method method = component.getClass().getMethod("setText", String.class);
			method.invoke(component, new Integer(attributeValue).toString());
		}else if(clazz == JComboBox.class){
			Method method = component.getClass().getMethod("setSelectedIndex", int.class);
			if(null != equipmentIndex){
				method.invoke(component, attributeValue==-1?0:(attributeValue-map.get(equipmentIndex)));
			}else{
				method.invoke(component, attributeValue+1);
			}
		}
	}
	
	/*��ȡ��Ǯ����ɫ���Լ�װ������Ʒ*/
	private boolean getRoleAttributeAndArticles(String filename, int roleIndex){
		// HP��MP��ATT��DEF��MAG���̱���������������0xFFFFFFFF��ʽ
		//���顢SPD�����ˡ��ں������ڲ���0x00000000��ʽ
		//HP��MP��ATT��DEF��MAG���̱���������������SPD���ֵ999
		//���ˡ��ں������ڲ����ֵ99
		//�������ֵ999999
		RandomAccessFile saveFile = null;
		if(filename==null){
			JOptionPane.showMessageDialog(mainFrame, "�浵�ļ���Ϊ��", "�浵�ļ�����", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if("".equals(filename.trim())){
			JOptionPane.showMessageDialog(mainFrame, "�浵�ļ���Ϊ��", "�浵�ļ�����", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(roleIndex==-1){
			JOptionPane.showMessageDialog(mainFrame, "���ȡ�浵�ļ���ѡ��һ����ɫ", "��ɫ����", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		try {
				saveFile = new RandomAccessFile(filename,"rw");
				
				/*4�ֽ����Ժ�װ������Ʒ*/
				byte[] b = new byte[4];
				
				/*��Ǯ*/
				getValueByAddress(saveFile, b, moneyText, addressMap, 0, "money","changeToIntA", null);
				getValueByAddress(saveFile, b, hp, addressMap, roleIndex, "hp","changeToIntA", null);
				getValueByAddress(saveFile, b, mp, addressMap, roleIndex, "mp","changeToIntA", null);
				getValueByAddress(saveFile, b, experience, addressMap, roleIndex, "experience","changeToIntB", null);
				getValueByAddress(saveFile, b, att, addressMap, roleIndex, "att","changeToIntA", null);
				getValueByAddress(saveFile, b, def, addressMap, roleIndex, "def","changeToIntA", null);
				getValueByAddress(saveFile, b, mag, addressMap, roleIndex, "mag","changeToIntA", null);
				getValueByAddress(saveFile, b, spd, addressMap, roleIndex, "spd","changeToIntB", null);
				getValueByAddress(saveFile, b, luck, addressMap, roleIndex, "luck","changeToIntB", null);
				getValueByAddress(saveFile, b, sword, addressMap, roleIndex, "sword","changeToIntA", null);
				getValueByAddress(saveFile, b, pike, addressMap, roleIndex, "pike","changeToIntA", null);
				getValueByAddress(saveFile, b, bow, addressMap, roleIndex, "bow","changeToIntA", null);
				getValueByAddress(saveFile, b, swordLearning, addressMap, roleIndex, "swordLearning","changeToIntB", null);
				getValueByAddress(saveFile, b, pikeLearning, addressMap, roleIndex, "pikeLearning","changeToIntB", null);
				getValueByAddress(saveFile, b, bowLearning, addressMap, roleIndex, "bowLearning","changeToIntB", null);
				getValueByAddress(saveFile, b, magicLearning, addressMap, roleIndex, "magicLearning","changeToIntB", null);
				getValueByAddress(saveFile, b, integration, addressMap, roleIndex, "integration","changeToIntB", null);
				getValueByAddress(saveFile, b, eloquence, addressMap, roleIndex, "eloquence","changeToIntB", null);
				getValueByAddress(saveFile, b, helmet, addressMap, roleIndex, "helmet","changeToIntB", "helmetIndex");
				getValueByAddress(saveFile, b, clothes, addressMap, roleIndex, "clothes","changeToIntB", "clothesIndex");
				getValueByAddress(saveFile, b, weapon, addressMap, roleIndex, "weapon","changeToIntB", "weaponIndex");
				getValueByAddress(saveFile, b, shoes, addressMap, roleIndex, "shoes","changeToIntB", "shoesIndex");
				getValueByAddress(saveFile, b, ornament, addressMap, roleIndex, "ornament","changeToIntB", "ornamentIndex");
				getValueByAddress(saveFile, b, itemA, addressMap, roleIndex, "itemA","changeToIntB", null);
				getValueByAddress(saveFile, b, itemB, addressMap, roleIndex, "itemB","changeToIntB", null);
				getValueByAddress(saveFile, b, itemC, addressMap, roleIndex, "itemC","changeToIntB", null);
				getValueByAddress(saveFile, b, itemD, addressMap, roleIndex, "itemD","changeToIntB", null);
				getValueByAddress(saveFile, b, itemE, addressMap, roleIndex, "itemE","changeToIntB", null);
				getValueByAddress(saveFile, b, itemF, addressMap, roleIndex, "itemF","changeToIntB", null);
				getValueByAddress(saveFile, b, itemG, addressMap, roleIndex, "itemG","changeToIntB", null);
				getValueByAddress(saveFile, b, itemH, addressMap, roleIndex, "itemH","changeToIntB", null);

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(mainFrame, "�浵�ļ�������", "�浵�ļ�����", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(mainFrame, "��ȡ�浵�ļ�����", "�浵�ļ�����", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (SecurityException e) {
				JOptionPane.showMessageDialog(mainFrame, "����ϵ����", "��������", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(mainFrame, "����ϵ����", "��������", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (NoSuchMethodException e) {
				JOptionPane.showMessageDialog(mainFrame, "����ϵ����", "��������", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (IllegalAccessException e) {
				JOptionPane.showMessageDialog(mainFrame, "����ϵ����", "��������", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (InvocationTargetException e) {
				JOptionPane.showMessageDialog(mainFrame, "����ϵ����", "��������", JOptionPane.ERROR_MESSAGE);
				return false;
			}finally{
				if(saveFile!=null){
					try {
						saveFile.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(mainFrame, "�رմ浵�ļ�������ر��޸�������������", "�浵�ļ�����", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		return true;
	}
	
	/*�����Ǯ����ɫ���Լ�װ����Ʒ*/
	private boolean setRoleAttributeAndArticles(String filename, int roleIndex){
		// HP��MP��ATT��DEF��MAG���̱���������������0xFFFFFFFF��ʽ
		//���顢SPD�����ˡ��ں������ڲ���0x00000000��ʽ
		//HP��MP��ATT��DEF��MAG���̱���������������SPD���ֵ999
		//���ˡ��ں������ڲ����ֵ99
		//�������ֵ999999
		RandomAccessFile saveFile = null;
		if(filename==null){
			JOptionPane.showMessageDialog(mainFrame, "�浵�ļ���Ϊ��", "�浵�ļ�����", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if("".equals(filename.trim())){
			JOptionPane.showMessageDialog(mainFrame, "�浵�ļ���Ϊ��", "�浵�ļ�����", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(roleIndex==-1){
			JOptionPane.showMessageDialog(mainFrame, "���ȡ�浵�ļ���ѡ��һ����ɫ", "��ɫ����", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try{
				
				if(checkvalues()){
					saveFile = new RandomAccessFile(filename,"rw");
					
					/*4�ֽ����Ժ�װ������Ʒ*/
					byte[] b = new byte[4];
					setValueByAddress(saveFile, b, moneyText, addressMap, null, 0, "money", "changeToBytesA");
					setValueByAddress(saveFile, b, hp, addressMap, null, roleIndex, "hp", "changeToBytesA");
					setValueByAddress(saveFile, b, mp, addressMap, null, roleIndex, "mp", "changeToBytesA");
					setValueByAddress(saveFile, b, experience, addressMap, null, roleIndex, "experience", "changeToBytesB");
					setValueByAddress(saveFile, b, att, addressMap, null, roleIndex, "att", "changeToBytesA");
					setValueByAddress(saveFile, b, def, addressMap, null, roleIndex, "def", "changeToBytesA");
					setValueByAddress(saveFile, b, mag, addressMap, null, roleIndex, "mag", "changeToBytesA");
					setValueByAddress(saveFile, b, spd, addressMap, null, roleIndex, "spd", "changeToBytesB");
					setValueByAddress(saveFile, b, luck, addressMap, null, roleIndex, "luck", "changeToBytesB");
					setValueByAddress(saveFile, b, sword, addressMap, null, roleIndex, "sword", "changeToBytesA");
					setValueByAddress(saveFile, b, pike, addressMap, null, roleIndex, "pike", "changeToBytesA");
					setValueByAddress(saveFile, b, bow, addressMap, null, roleIndex, "bow", "changeToBytesA");
					setValueByAddress(saveFile, b, integration, addressMap, null, roleIndex, "integration", "changeToBytesB");
					setValueByAddress(saveFile, b, eloquence, addressMap, null, roleIndex, "eloquence", "changeToBytesB");
					setValueByAddress(saveFile, b, swordLearning, addressMap, null, roleIndex, "swordLearning", "changeToBytesB");
					setValueByAddress(saveFile, b, pikeLearning, addressMap, null, roleIndex, "pikeLearning", "changeToBytesB");
					setValueByAddress(saveFile, b, bowLearning, addressMap, null, roleIndex, "bowLearning", "changeToBytesB");
					setValueByAddress(saveFile, b, magicLearning, addressMap, null, roleIndex, "magicLearning", "changeToBytesB");
					setValueByAddress(saveFile, b, helmet, addressMap, articlesMap, roleIndex, "helmet", "changeToBytesB");
					setValueByAddress(saveFile, b, clothes, addressMap, articlesMap, roleIndex, "clothes", "changeToBytesB");
					setValueByAddress(saveFile, b, weapon, addressMap, articlesMap, roleIndex, "weapon", "changeToBytesB");
					setValueByAddress(saveFile, b, shoes, addressMap, articlesMap, roleIndex, "shoes", "changeToBytesB");
					setValueByAddress(saveFile, b, ornament, addressMap, articlesMap, roleIndex, "ornament", "changeToBytesB");
					setValueByAddress(saveFile, b, itemA, addressMap, articlesMap, roleIndex, "itemA", "changeToBytesB");
					setValueByAddress(saveFile, b, itemB, addressMap, articlesMap, roleIndex, "itemB", "changeToBytesB");
					setValueByAddress(saveFile, b, itemC, addressMap, articlesMap, roleIndex, "itemC", "changeToBytesB");
					setValueByAddress(saveFile, b, itemD, addressMap, articlesMap, roleIndex, "itemD", "changeToBytesB");
					setValueByAddress(saveFile, b, itemE, addressMap, articlesMap, roleIndex, "itemE", "changeToBytesB");
					setValueByAddress(saveFile, b, itemF, addressMap, articlesMap, roleIndex, "itemF", "changeToBytesB");
					setValueByAddress(saveFile, b, itemG, addressMap, articlesMap, roleIndex, "itemG", "changeToBytesB");
					setValueByAddress(saveFile, b, itemH, addressMap, articlesMap, roleIndex, "itemH", "changeToBytesB");
				}else{
					StringBuilder sb = new StringBuilder();
					sb.append("��ȷ�������������Ϊ������\n");
					sb.append("1.����ֵ��ħ��ֵ���﹥��������ħ�����̱����������������ٶ���0-999֮��\n");
					sb.append("2.���ˡ��ں������ڲš��̱��ɳ��������ɳ��������ɳ���ħ���ɳ���0-99֮��\n");
					sb.append("3.����ֵ����Ǯ��0-999999֮��\n");
					JOptionPane.showMessageDialog(mainFrame, sb, "�������ݷǷ�", JOptionPane.ERROR_MESSAGE);
				}
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(mainFrame, "�浵�ļ�������", "�浵�ļ�����", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(mainFrame, "��ȡ�浵�ļ�����", "�浵�ļ�����", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (SecurityException e) {
				JOptionPane.showMessageDialog(mainFrame, "����ϵ����", "��������", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(mainFrame, "����ϵ����", "��������", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (NoSuchMethodException e) {
				JOptionPane.showMessageDialog(mainFrame, "����ϵ����", "��������", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (IllegalAccessException e) {
				JOptionPane.showMessageDialog(mainFrame, "����ϵ����", "��������", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (InvocationTargetException e) {
				JOptionPane.showMessageDialog(mainFrame, "����ϵ����", "��������", JOptionPane.ERROR_MESSAGE);
				return false;
			}finally{
				if(saveFile!=null){
					try {
						saveFile.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(mainFrame, "�رմ浵�ļ�������ر��޸�������������", "�浵�ļ�����", JOptionPane.WARNING_MESSAGE);
					}
				}
			}	
		return true;
	}
	
	/*0xFFFFFFFFת��*/
	private void changeToBytesA(int value, byte[] b){
		/*if(value == 0){
			b[0] = b[1] = b[2] = b[3] = 0;
			return;
		}
		b[0] = (byte)(256-value&0x000000FF);
		b[1] = (byte)(~((value&0x0000FF00)>>>8));
		b[1] = b[0]==0?(byte)(b[1]+1):b[1];
		b[2] = (byte)(~((value&0x00FF0000)>>>16));
		b[3] = (byte)(~((value&0xFF000000)>>>24));*/
		b[0] = (byte)((-value)&0x000000FF);
		b[1] = (byte)(((-value)&0x0000FF00)>>>8);
		b[2] = (byte)(((-value)&0x00FF0000)>>>16);
		b[3] = (byte)(((-value)&0xFF000000)>>>24);
	}
	
	
	/*0x00000000ת��*/
	private void changeToBytesB(int value, byte[] b){
		b[0] = (byte)(value&0x000000FF);
		b[1] = (byte)((value&0x0000FF00)>>>8);
		b[2] = (byte)((value&0x00FF0000)>>>16);
		b[3] = (byte)((value&0xFF000000)>>>24);
	}
	
	/*0xFFFFFFFFת��*/
	private int changeToIntA(byte[] b){
		/*int b0 = 256-(b[0]&0x000000FF);
		int b1 = ((~b[1])&0x000000FF);
		int b2 = ((~b[2])&0x000000FF);
		int b3 = ((~b[3])&0x000000FF);
		return ((b3*256+b2)*256+b1)*256+b0;*/
		return -((b[0]&0x000000FF)|((b[1]&0x000000FF)<<8)|((b[2]&0x000000FF)<<16)|((b[3]&0x000000FF)<<24));
	}
	
	/*0x00000000ת��*/
	private int changeToIntB(byte[] b){
		int b0 = (b[0]&0x000000FF);
		int b1 = (b[1]&0x000000FF);
		int b2 = (b[2]&0x000000FF);
		int b3 = (b[3]&0x000000FF);
		return ((b3*256+b2)*256+b1)*256+b0;
	}
	
	/*�����������ֵ�ĺϷ���*/
	private boolean checkvalues(){
		// HP��MP��ATT��DEF��MAG���̱�����������������Ǯ��0xFFFFFFFF��ʽ
		//���顢SPD�����ˡ��ں������ڲ���0x00000000��ʽ
		//HP��MP��ATT��DEF��MAG���̱���������������SPD���ֵ999
		//���ˡ��ں������ڲ����ֵ99
		//��Ǯ���������ֵ999999
		try{
			int moneyValue = Integer.parseInt(moneyText.getText());
			int hpValue = Integer.parseInt(hp.getText());
			int mpValue = Integer.parseInt(mp.getText());
			int attValue = Integer.parseInt(att.getText());
			int defValue = Integer.parseInt(def.getText());
			int magValue = Integer.parseInt(mag.getText());
			int swordValue = Integer.parseInt(sword.getText());
			int pikeValue = Integer.parseInt(pike.getText());
			int bowValue = Integer.parseInt(bow.getText());
			int experienceValue = Integer.parseInt(experience.getText());
			int spdValue = Integer.parseInt(spd.getText());
			int luckValue = Integer.parseInt(luck.getText());
			int integrationValue = Integer.parseInt(integration.getText());
			int eloquenceValue = Integer.parseInt(eloquence.getText());
			int swordLearningValue = Integer.parseInt(swordLearning.getText());
			int pikeLearningValue = Integer.parseInt(pikeLearning.getText());
			int bowLearningValue = Integer.parseInt(bowLearning.getText());
			int magicLearningValue = Integer.parseInt(magicLearning.getText());
			
			if(moneyValue>999999||moneyValue<0) return false;
			if(hpValue>999||hpValue<0) return false;
			if(mpValue>999||mpValue<0) return false;
			if(attValue>999||attValue<0) return false;
			if(defValue>999||defValue<0) return false;
			if(magValue>999||magValue<0) return false;
			if(swordValue>999||swordValue<0) return false;
			if(pikeValue>999||pikeValue<0) return false;
			if(bowValue>999||bowValue<0) return false;
			if(experienceValue>999999||experienceValue<0) return false;
			if(spdValue>999||spdValue<0) return false;
			if(luckValue>99||luckValue<0) return false;
			if(integrationValue>99||integrationValue<0) return false;
			if(eloquenceValue>99||eloquenceValue<0) return false;
			if(swordLearningValue>99||swordLearningValue<0) return false;
			if(pikeLearningValue>99||pikeLearningValue<0) return false;
			if(bowLearningValue>99||bowLearningValue<0) return false;
			if(magicLearningValue>99||magicLearningValue<0) return false;
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	/*�浵�ļ����͹�������*/
	class ExtensionFileFilter extends FileFilter{
		private String description;
		private ArrayList<String> extensions = new ArrayList<String>();
		
		public void addExtension(String extension){
			if(!extension.startsWith(".")){
				extension = "." + extension;
				extensions.add(extension.toLowerCase());
			}
		}
		
		public void setDescription(String description) {
			this.description = description;
		}

		@Override
		public boolean accept(File f) {
			if(f.isDirectory()) return true;
			String name = f.getName().toLowerCase();
			for(String extension:extensions){
				if(name.endsWith(extension)){
					return true;
				}
			}
			return false;
		}

		@Override
		public String getDescription() {
			return description;
		}
	}
	
/********************************************************���´�������ʼ��********************************************************/
	/*���沼�ֳ�ʼ��*/
	private void uiInit(){
		/*northPanel*/
		filenameText.setEditable(false);
		openFilePanel.add(filenameText);
		openFilePanel.add(openButton);
		openFilePanel.add(readButton);
		moneyPanel.add(moneyLabel);
		moneyPanel.add(moneyText);
		northPanel.setLayout(new BorderLayout());
		northPanel.add(openFilePanel, BorderLayout.NORTH);
		northPanel.add(moneyPanel, BorderLayout.SOUTH);
		
		
		/*rolesPanel*/
		rolesList.setVisibleRowCount(20);
		rolesList.setFixedCellWidth(80);
		rolesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		roleScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		rolesPanel.setPreferredSize(new Dimension(120,250));
		rolesPanel.setBorder(new TitledBorder(new EtchedBorder(),"��ɫ"));
		rolesPanel.add(roleScrollPane);
		
		
		/*attributesPanel*/
		hpHorizontalBox.add(hpLabel);hpHorizontalBox.add(hp);
		mpHorizontalBox.add(mpLabel);mpHorizontalBox.add(mp);
		magicPowerHorizontalBox.add(magLabel);magicPowerHorizontalBox.add(mag);
		strengthHorizontalBox.add(attLabel);strengthHorizontalBox.add(att);
		physiqueHorizontalBox.add(defLabel);physiqueHorizontalBox.add(def);
		agilityHorizontalBox.add(spdLabel);agilityHorizontalBox.add(spd);
		luckHorizontalBox.add(luckLabel);luckHorizontalBox.add(luck);
		swordHorizontalBox.add(swordLabel);swordHorizontalBox.add(sword);
		pikeHorizontalBox.add(pikeLabel);pikeHorizontalBox.add(pike);
		bowHorizontalBox.add(bowLabel);bowHorizontalBox.add(bow);
		integrationHorizontalBox.add(integrationLabel);integrationHorizontalBox.add(integration);
		eloquenceHorizontalBox.add(eloquenceLabel);eloquenceHorizontalBox.add(eloquence);
		experienceHorizontalBox.add(experienceLabel);experienceHorizontalBox.add(experience);
		allMagicHorizontalBox.add(setAllMagicButton);
		swordLearningHorizontalBox.add(swordLearningLabel);swordLearningHorizontalBox.add(swordLearning);
		pikeLearningHorizontalBox.add(pikeLearningLabel);pikeLearningHorizontalBox.add(pikeLearning);
		bowLearningHorizontalBox.add(bowLearningLabel);bowLearningHorizontalBox.add(bowLearning);
		magicLearningHorizontalBox.add(magicLearningLabel);magicLearningHorizontalBox.add(magicLearning);
		
		attributesVerticalBox.add(hpHorizontalBox);
		attributesVerticalBox.add(mpHorizontalBox);
		attributesVerticalBox.add(magicPowerHorizontalBox);
		attributesVerticalBox.add(strengthHorizontalBox);
		attributesVerticalBox.add(physiqueHorizontalBox);
		attributesVerticalBox.add(agilityHorizontalBox);
		attributesVerticalBox.add(swordLearningHorizontalBox);
		attributesVerticalBox.add(pikeLearningHorizontalBox);
		attributesVerticalBox.add(bowLearningHorizontalBox);
		attributesVerticalBox.add(magicLearningHorizontalBox);
		attributesVerticalBox.add(luckHorizontalBox);
		attributesVerticalBox.add(swordHorizontalBox);
		attributesVerticalBox.add(pikeHorizontalBox);
		attributesVerticalBox.add(bowHorizontalBox);
		attributesVerticalBox.add(integrationHorizontalBox);
		attributesVerticalBox.add(eloquenceHorizontalBox);
		attributesVerticalBox.add(experienceHorizontalBox);
		attributesVerticalBox.add(allMagicHorizontalBox);
		attributesPanel.add(attributesVerticalBox);
		attributesPanel.setBorder(new TitledBorder(new EtchedBorder(),"����"));
		
		
		/*articlesPanel*/
		articlesPanel.setPreferredSize(new Dimension(190,250));
		helmetHorizontalBox.add(helmetLabel);	helmetHorizontalBox.add(helmet);
		clothesHorizontalBox.add(clothesLabel);	clothesHorizontalBox.add(clothes);
		weaponHorizontalBox.add(weaponLabel);	weaponHorizontalBox.add(weapon);
		shoesHorizontalBox.add(shoesLabel);		shoesHorizontalBox.add(shoes);
		ornamentHorizontalBox.add(ornamentLabel);		ornamentHorizontalBox.add(ornament);
		equipmentsVerticalBox.add(helmetHorizontalBox);
		equipmentsVerticalBox.add(clothesHorizontalBox);
		equipmentsVerticalBox.add(weaponHorizontalBox);
		equipmentsVerticalBox.add(shoesHorizontalBox);
		equipmentsVerticalBox.add(ornamentHorizontalBox);
		equipmentsPanel.add(equipmentsVerticalBox);
		equipmentsPanel.setBorder(new TitledBorder(new EtchedBorder(),"װ��"));
		articlesPanel.add(equipmentsPanel);
		
		itemHorizontalBoxA.add(itemLabelA);		itemHorizontalBoxA.add(itemA);
		itemHorizontalBoxB.add(itemLabelB);		itemHorizontalBoxB.add(itemB);
		itemHorizontalBoxC.add(itemLabelC);		itemHorizontalBoxC.add(itemC);
		itemHorizontalBoxD.add(itemLabelD);		itemHorizontalBoxD.add(itemD);
		itemHorizontalBoxE.add(itemLabelE);		itemHorizontalBoxE.add(itemE);
		itemHorizontalBoxF.add(itemLabelF);		itemHorizontalBoxF.add(itemF);
		itemHorizontalBoxG.add(itemLabelG);		itemHorizontalBoxG.add(itemG);
		itemHorizontalBoxH.add(itemLabelH);		itemHorizontalBoxH.add(itemH);
		itemsVerticalBox.add(itemHorizontalBoxA);
		itemsVerticalBox.add(itemHorizontalBoxB);
		itemsVerticalBox.add(itemHorizontalBoxC);
		itemsVerticalBox.add(itemHorizontalBoxD);
		itemsVerticalBox.add(itemHorizontalBoxE);
		itemsVerticalBox.add(itemHorizontalBoxF);
		itemsVerticalBox.add(itemHorizontalBoxG);
		itemsVerticalBox.add(itemHorizontalBoxH);
		itemsPanel.add(itemsVerticalBox);
		itemsPanel.setBorder(new TitledBorder(new EtchedBorder(),"��Ʒ"));
		articlesPanel.add(itemsPanel);
		
		/*saveButton*/
		saveButton.setFont(new Font("����",Font.BOLD,20));
		
		/*mainFrame*/
		mainFrame.setBounds(250, 100, 480, 580);
		mainFrame.add(northPanel, BorderLayout.NORTH);
		mainFrame.add(rolesPanel, BorderLayout.WEST);
		mainFrame.add(attributesPanel, BorderLayout.CENTER);
		mainFrame.add(articlesPanel, BorderLayout.EAST);
		mainFrame.add(saveButton, BorderLayout.SOUTH);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
	}
	
	
	/*������Ʒ���Ƴ�ʼ��*/
	private static void articlesListInit(){
		articlesList.clear();
		articlesList.add("<��>");
		articlesList.add("ľ��");
		articlesList.add("����");
		articlesList.add("�̸�");
		articlesList.add("˫�г���");
		articlesList.add("�����䵶");
		articlesList.add("���ս�");
		articlesList.add("���߱���");
		articlesList.add("�ǻ���");
		articlesList.add("������");
		articlesList.add("����֮��");
		articlesList.add("�����䵶");
		articlesList.add("���鱦��");
		articlesList.add("��ͷ��");
		articlesList.add("���佣");
		articlesList.add("������");
		articlesList.add("����֮��");
		articlesList.add("���㳤�̽�");
		articlesList.add("��ʹ��");
		articlesList.add("��ʿ��");
		articlesList.add("�����䵶");
		articlesList.add("����");
		articlesList.add("���Ǳ���");
		articlesList.add("ն����");
		articlesList.add("����֮��");
		articlesList.add("�λ���");
		articlesList.add("�¿���֮��");
		articlesList.add("ľǹ");
		articlesList.add("����");
		articlesList.add("����");
		articlesList.add("��ǹ");
		articlesList.add("��ǹ");
		articlesList.add("�ӳ���������");
		articlesList.add("�������");
		articlesList.add("���鳤��");
		articlesList.add("����ǹ");
		articlesList.add("����׶");
		articlesList.add("��Ӱǹ");
		articlesList.add("��֬�����");
		articlesList.add("�ڰ���ì");
		articlesList.add("������ʹ��");
		articlesList.add("����ǹ");
		articlesList.add("��ŭ�ͻ�ǹ");
		articlesList.add("����ǹ");
		articlesList.add("����ì");
		articlesList.add("�λ���ǹ");
		articlesList.add("�ƽ���ǹ");
		articlesList.add("ľ��");
		articlesList.add("��̥��");
		articlesList.add("���ٹ�");
		articlesList.add("���ǹ�");
		articlesList.add("������");
		articlesList.add("���ɹ�");
		articlesList.add("��β��");
		articlesList.add("ħ�ƹ�");
		articlesList.add("����");
		articlesList.add("���鹭");
		articlesList.add("ǿ����");
		articlesList.add("ʮ�ֹ�");
		articlesList.add("������");
		articlesList.add("��ʿ��");
		articlesList.add("����ǿ����");
		articlesList.add("������");
		articlesList.add("��צ��");
		articlesList.add("�λ�ʮ�ֹ�");
		articlesList.add("��Ʒ���蹭");
		articlesList.add("ʫ��ǿ��");
		articlesList.add("��ľħ��");
		articlesList.add("�ڰ�ɭ��");
		articlesList.add("˫����");
		articlesList.add("����֮��");
		articlesList.add("�����ض�");
		articlesList.add("ħʯ��");
		articlesList.add("��������");
		articlesList.add("��ɫ���˹");
		articlesList.add("���֮��");
		articlesList.add("ϣ��֮��");
		articlesList.add("�ƽ�֮��");
		articlesList.add("�������");
		articlesList.add("������");
		articlesList.add("�������");
		articlesList.add("�������");
		articlesList.add("�λô��");
		articlesList.add("����");
		articlesList.add("����֮��");
		articlesList.add("��ľ��");
		articlesList.add("����ͷ��");
		articlesList.add("ͭ��");
		articlesList.add("������");
		articlesList.add("��ñ");
		articlesList.add("�����Ƥ��");
		articlesList.add("�������");
		articlesList.add("ԡѪ���");
		articlesList.add("ҹɫ֮��");
		articlesList.add("�۷�֮��");
		articlesList.add("˫��֮��");
		articlesList.add("��裿�");
		articlesList.add("�����");
		articlesList.add("����֮��");
		articlesList.add("�ƽ����");
		articlesList.add("�����");
		articlesList.add("ˮ����");
		articlesList.add("�λ�֮��");
		articlesList.add("����֮��");
		articlesList.add("ʥ��֮��");
		articlesList.add("�Ʒ�");
		articlesList.add("��ľ��");
		articlesList.add("��ľ��");
		articlesList.add("ӲƤ��");
		articlesList.add("����");
		articlesList.add("�����");
		articlesList.add("���֮��");
		articlesList.add("ԡѪ����");
		articlesList.add("ħ��֮��");
		articlesList.add("��ʿ����");
		articlesList.add("�ضܼ�");
		articlesList.add("�������");
		articlesList.add("��������");
		articlesList.add("��������");
		articlesList.add("��ʥ��");
		articlesList.add("֩��ħ��");
		articlesList.add("ħ������");
		articlesList.add("��ɫ֮��");
		articlesList.add("���Ϻڼ�");
		articlesList.add("������֮��");
		articlesList.add("��ѥ");
		articlesList.add("��ľѥ");
		articlesList.add("�����Ƥѥ");
		articlesList.add("����֮ѥ");
		articlesList.add("ɮѥ");
		articlesList.add("¹Ƥ��ѥ");
		articlesList.add("�໢Ƥѥ");
		articlesList.add("����Ƥѥ");
		articlesList.add("���˳�ѥ");
		articlesList.add("̤��ѥ");
		articlesList.add("��ʿѥ");
		articlesList.add("��ʹѥ");
		articlesList.add("����ѥ");
		articlesList.add("����Ƥѥ");
		articlesList.add("����ѥ");
		articlesList.add("ʥ��ѥ");
		articlesList.add("����ѥ");
		articlesList.add("��Ӱ֮ѥ");
		articlesList.add("����ѥ");
		articlesList.add("ѩŮ֮ѥ");
		articlesList.add("��ħ֮��");
		articlesList.add("��֮��");
		articlesList.add("Ů��֮��");
		articlesList.add("����֮��");
		articlesList.add("�챦ʯ��ָ");
		articlesList.add("����ʯ��ָ");
		articlesList.add("���ʯ��ָ");
		articlesList.add("�����ָ");
		articlesList.add("�߲��ֻ�");
		articlesList.add("�ƽ��ֻ�");
		articlesList.add("�����ֻ�");
		articlesList.add("�ٶ��ֻ�");
		articlesList.add("�����ֻ�");
		articlesList.add("�����ֻ�");
		articlesList.add("����������");
		articlesList.add("��è����");
		articlesList.add("��������");
		articlesList.add("�������");
		articlesList.add("����������");
		articlesList.add("ľ�񻤷�");
		articlesList.add("��֮����");
		articlesList.add("��֮����");
		articlesList.add("��֮����");
		articlesList.add("��֮����");
		articlesList.add("��֮����");
		articlesList.add("��֮����");
		articlesList.add("��֮����");
		articlesList.add("��֮����");
		articlesList.add("ˮ֮����");
		articlesList.add("��֮����");
		articlesList.add("ħ������");
		articlesList.add("ף������");
		articlesList.add("��������");
		articlesList.add("��������");
		articlesList.add("��ʹ����");
		articlesList.add("��������������");
		articlesList.add("�޳ֻ���������");
		articlesList.add("ǿ������������");
		articlesList.add("��׼����������");
		articlesList.add("�費���������");
		articlesList.add("�����ױ�������");
		articlesList.add("�μ��ױ�������");
		articlesList.add("�м��ױ�������");
		articlesList.add("��������������");
		articlesList.add("�޳ֱ���������");
		articlesList.add("ǿ������������");
		articlesList.add("��׼����������");
		articlesList.add("�貱���������");
		articlesList.add("������ѩ������");
		articlesList.add("�μ���ѩ������");
		articlesList.add("�м���ѩ������");
		articlesList.add("��������������");
		articlesList.add("�޳ֻ���������");
		articlesList.add("ǿ������������");
		articlesList.add("��׼����������");
		articlesList.add("�費���������");
		articlesList.add("��������������");
		articlesList.add("�μ�����������");
		articlesList.add("�м�����������");
		articlesList.add("����ħ��������");
		articlesList.add("�޳�ħ��������");
		articlesList.add("ǿ��ħ��������");
		articlesList.add("��׼ħ��������");
		articlesList.add("��ħ��������");
		articlesList.add("��������������");
		articlesList.add("�μ�����������");
		articlesList.add("�м�����������");
		articlesList.add("������ʯ������");
		articlesList.add("�޳���ʯ������");
		articlesList.add("ǿ����ʯ������");
		articlesList.add("��׼��ʯ������");
		articlesList.add("����ʯ������");
		articlesList.add("�������������");
		articlesList.add("�μ����������");
		articlesList.add("�м����������");
		articlesList.add("�����׻�������");
		articlesList.add("�޳��׻�������");
		articlesList.add("ǿ���׻�������");
		articlesList.add("��׼�׻�������");
		articlesList.add("���׻�������");
		articlesList.add("��������������");
		articlesList.add("�μ�����������");
		articlesList.add("�м�����������");
		articlesList.add("��Ч̱��������");
		articlesList.add("ǿ��̱��������");
		articlesList.add("��Ч�ж�������");
		articlesList.add("ǿ���ж�������");
		articlesList.add("��Ч����������");
		articlesList.add("ǿ�ƻ���������");
		articlesList.add("��Ч����������");
		articlesList.add("ǿ�ƻ���������");
		articlesList.add("�ƶ�������");
		articlesList.add("�ⶾ������");
		articlesList.add("����������");
		articlesList.add("����������");
		articlesList.add("��β��");
		articlesList.add("�콬��");
		articlesList.add("ѩɽ꼻�");
		articlesList.add("����֮ˮ");
		articlesList.add("���˵ĵ�");
		articlesList.add("�¿���֮��");
		articlesList.add("����������");
		articlesList.add("è��ʯ");
		articlesList.add("��ɫˮ��ʯ");
		articlesList.add("������Ů֮��");
		articlesList.add("��ͷ������");
		articlesList.add("�ڰ�֮��");
		articlesList.add("����֮��");
		articlesList.add("ɳĮ��¶");
		articlesList.add("�����");
		articlesList.add("�Ե���");
		articlesList.add("���ﰲ�ı�Ǭ");
		articlesList.add("Ѫ������");
		articlesList.add("�������β��");
		articlesList.add("���޹���");
		articlesList.add("����֮��");
		articlesList.add("�ƽ����ʥˮ");
		articlesList.add("��ʯ֮��");
		articlesList.add("Ϻ����");
		articlesList.add("Ыβ��");
		articlesList.add("���ŵ�ͷ��");
		articlesList.add("��ħ������");
		articlesList.add("����ʯ");
		articlesList.add("����");
		articlesList.add("��ż֮�");
		articlesList.add("��ת�糵");
		articlesList.add("�߲�Ģ��");
		articlesList.add("��ʹ֮��");
		articlesList.add("������");
		articlesList.add("���");
		articlesList.add("��Ũ�����⾫");
		articlesList.add("����֮��");
		articlesList.add("ѩ֮��");
		articlesList.add("����ʯ");
		articlesList.add("�̾�ʯ");
		articlesList.add("Ժ�����˵Ļ���");
		articlesList.add("��צè");
		articlesList.add("è֮ҩ��");
		articlesList.add("��Ȯ");
		articlesList.add("ǿ������");
		articlesList.add("������");
		articlesList.add("Ů��ƿ");
		articlesList.add("������");
		articlesList.add("��֮��̨");
		articlesList.add("ħ��");
		articlesList.add("ħ��ʦ����");
		articlesList.add("��ŵ�߲ر�ͼ");
		articlesList.add("���");
		articlesList.add("ˮ��");
		articlesList.add("ħ��ˮ����");
		articlesList.add("ľ��С��ż");
		articlesList.add("����������");
		articlesList.add("�޳ֻ�����");
		articlesList.add("��ǿ��������");
		articlesList.add("�����������");
		articlesList.add("���λû�����");
		articlesList.add("�����ױ���");
		articlesList.add("����������");
		articlesList.add("�޳ֱ�����");
		articlesList.add("��ǿ��������");
		articlesList.add("�����������");
		articlesList.add("���λñ�����");
		articlesList.add("������ѩ��");
		articlesList.add("����������");
		articlesList.add("�޳ֻ�����");
		articlesList.add("��ǿ��������");
		articlesList.add("�����������");
		articlesList.add("���λû�����");
		articlesList.add("����������");
		articlesList.add("����ħ����");
		articlesList.add("�޳�ħ����");
		articlesList.add("��ǿ��ħ����");
		articlesList.add("�����ħ����");
		articlesList.add("���λ�ħ����");
		articlesList.add("����������");
		articlesList.add("������ʯ��");
		articlesList.add("�޳���ʯ��");
		articlesList.add("��ǿ����ʯ��");
		articlesList.add("�������ʯ��");
		articlesList.add("���λ���ʯ��");
		articlesList.add("����������");
		articlesList.add("�����׻���");
		articlesList.add("�޳��׻���");
		articlesList.add("��ǿ���׻���");
		articlesList.add("������׻���");
		articlesList.add("���λ��׻���");
		articlesList.add("����������");
		articlesList.add("����̱����");
		articlesList.add("��Ч̱����");
		articlesList.add("ǿ��̱����");
		articlesList.add("�޳�̱����");
		articlesList.add("Ⱥ��̱����");
		articlesList.add("�����ж���");
		articlesList.add("��Ч�ж���");
		articlesList.add("ǿ���ж���");
		articlesList.add("�޳��ж���");
		articlesList.add("Ⱥ���ж���");
		articlesList.add("����������");
		articlesList.add("��Ч������");
		articlesList.add("ǿ�ƻ�����");
		articlesList.add("�޳ֻ�����");
		articlesList.add("Ⱥ��������");
		articlesList.add("����������");
		articlesList.add("��Ч������");
		articlesList.add("ǿ�ƻ�����");
		articlesList.add("�޳ֻ�����");
		articlesList.add("Ⱥ��������");
		articlesList.add("�ƶ���");
		articlesList.add("Ⱥ���ƶ���");
		articlesList.add("�ⶾ��");
		articlesList.add("Ⱥ��ⶾ��");
		articlesList.add("������");
		articlesList.add("Ⱥ�������");
		articlesList.add("������");
		articlesList.add("Ⱥ�廽����");
		articlesList.add("С�л���");
		articlesList.add("�ил���");
		articlesList.add("С������");
		articlesList.add("��������");
		articlesList.add("��������");
		articlesList.add("Сҽ����");
		articlesList.add("��ҽ����");
		articlesList.add("��ҽ����");
		articlesList.add("С������");
		articlesList.add("�й�����");
		articlesList.add("СѪ����");
		articlesList.add("��Ѫ����");
		articlesList.add("С������");
		articlesList.add("�г�����");
		articlesList.add("С������");
		articlesList.add("С������");
		articlesList.add("�л�����");
		articlesList.add("������");
		articlesList.add("С������");
		articlesList.add("�г�����");
		articlesList.add("��������");
		articlesList.add("С�ض���");
		articlesList.add("���ض���");
		articlesList.add("�ܼ���");
		articlesList.add("��������");
		articlesList.add("С������");
		articlesList.add("�г�����");
		articlesList.add("������");
		articlesList.add("С������");
		articlesList.add("�м�����");
	}
	
	/*���ֵ�ַ��ʼ��*/
	private void addressMapInit(){
		addressMap.clear();
		addressMap.put("money", 0x0004);
		addressMap.put("roles", 0x2348);
		addressMap.put("distanceBetweenRoles", 0x262C-0x2348);
		addressMap.put("hp", 0x2364);
		addressMap.put("mp", 0x2368);
		addressMap.put("experience", 0x236C);
		addressMap.put("att", 0x2370);
		addressMap.put("def", 0x2374);
		addressMap.put("mag", 0x2378);
		addressMap.put("spd", 0x237C);
		addressMap.put("luck", 0x2380);
		addressMap.put("sword", 0x238C);
		addressMap.put("pike", 0x2390);
		addressMap.put("bow", 0x2394);
		addressMap.put("swordLearning", 0x2398);
		addressMap.put("pikeLearning", 0x239C);
		addressMap.put("bowLearning", 0x23A0);
		addressMap.put("magicLearning", 0x23B0);
		addressMap.put("integration", 0x23B4);
		addressMap.put("eloquence", 0x23B8);
		addressMap.put("helmet", 0x23C0);
		addressMap.put("clothes", 0x23C4);
		addressMap.put("weapon", 0x23C8);
		addressMap.put("shoes", 0x23CC);
		addressMap.put("ornament", 0x23D0);
		addressMap.put("itemA", 0x23D4);
		addressMap.put("itemB", 0x23D8);
		addressMap.put("itemC", 0x23DC);
		addressMap.put("itemD", 0x23E0);
		addressMap.put("itemE", 0x23E4);
		addressMap.put("itemF", 0x23E8);
		addressMap.put("itemG", 0x23EC);
		addressMap.put("itemH", 0x23F0);
		addressMap.put("magic", 0x23F4);
		/*��������Ʒ�����ƫ����*/
		addressMap.put("weaponIndex", -1);
		addressMap.put("helmetIndex", 83);
		addressMap.put("clothesIndex", 103);
		addressMap.put("shoesIndex", 123);
		addressMap.put("ornamentIndex", 143);
		addressMap.put("itemIndex", 238);
	}
	
	/*������Ʒ�����ʼ��*/
	private void articlesMapInit(){
		articlesMap.clear();
		int value = -1;
		for(String s:articlesList){
			articlesMap.put(s, value);
			value++;
		}
	}
}
