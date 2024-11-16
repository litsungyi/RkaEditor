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
 * 学生骑士团修改器 
 */
public class Editor {
	
	/*存放各种物品名称，用类变量，在生成对象之前初始化*/
	private static List<String> articlesList = new ArrayList<String>();
	
	static {
		articlesListInit();
	} 
	
	/*存放各种参数首地址*/
	HashMap<String, Integer> addressMap = new HashMap<String, Integer>();
	
	/*存放各种物品及编码*/
	HashMap<String, Integer> articlesMap = new HashMap<String, Integer>();
	
	/*存档文件名，包含绝对路径*/
	String archiveFilename=null;
	
	/*选择的角色序号，从0开始*/
	int roleSelectedIndex=-1;
	
	/*文件类型过滤器*/
	ExtensionFileFilter fileFilter = new ExtensionFileFilter();
	
	/*主面板，采用BorderLayout布局*/
	JFrame mainFrame = new JFrame("学生骑士团存档修改器");
		
	/*northPanel放在mainFrame的NORTH，包含openFilePanel、moneyPanel*/
	JPanel northPanel = new JPanel();
	
	/*openFilePanel包含openButton、readButton、filenameText*/
	JPanel openFilePanel = new JPanel();
	JFileChooser fileChooser = new JFileChooser(".");
	JButton openButton = new JButton("浏览");
	JButton readButton = new JButton("读取");
	JTextField filenameText = new JTextField(30);
	
	/*moneyPanel包含moneyText、moneyLabel*/
	JPanel moneyPanel = new JPanel();
	JTextField moneyText = new JTextField(35);
	JLabel moneyLabel = new JLabel("金钱");
	
	/*rolePanel放在mainFrame的WEST，包含roleScrollPane*/
	JPanel rolesPanel = new JPanel();
	JList rolesList = new JList(new String[]{" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "});
	JScrollPane roleScrollPane = new JScrollPane(rolesList);
	
	/*attributesPanel放在mainFrame的CENTER*/
	JPanel attributesPanel = new JPanel();
	private Box attributesVerticalBox = Box.createVerticalBox();
	private Box hpHorizontalBox = Box.createHorizontalBox();
	JLabel hpLabel = new JLabel("生  命  值    ");
	JTextField hp = new JTextField(5);//HP
	private Box mpHorizontalBox = Box.createHorizontalBox();
	JLabel mpLabel = new JLabel("魔  法  值    ");
	JTextField mp = new JTextField(5);//MP
	private Box magicPowerHorizontalBox = Box.createHorizontalBox();
	JLabel magLabel = new JLabel("魔        力    ");
	JTextField mag = new JTextField(5);//MAG
	private Box strengthHorizontalBox = Box.createHorizontalBox();
	JLabel attLabel = new JLabel("力        量    ");
	JTextField att = new JTextField(5);//ATT
	private Box physiqueHorizontalBox = Box.createHorizontalBox();
	JLabel defLabel = new JLabel("体        质    ");
	JTextField def = new JTextField(5);//DEF
	private Box agilityHorizontalBox = Box.createHorizontalBox();
	JLabel spdLabel = new JLabel("敏        捷    ");
	JTextField spd = new JTextField(5);//SPD
	private Box luckHorizontalBox = Box.createHorizontalBox();
	JLabel luckLabel = new JLabel("幸  运  度    ");
	JTextField luck = new JTextField(5);//幸运度
	private Box swordHorizontalBox = Box.createHorizontalBox();
	JLabel swordLabel = new JLabel("短  兵  器    ");
	JTextField sword = new JTextField(5);//短兵器
	private Box pikeHorizontalBox = Box.createHorizontalBox();
	JLabel pikeLabel = new JLabel("长  兵  器    ");
	JTextField pike = new JTextField(5);//长兵器
	private Box bowHorizontalBox = Box.createHorizontalBox();
	JLabel bowLabel = new JLabel("弓        箭    ");
	JTextField bow = new JTextField(5);//弓箭
	private Box integrationHorizontalBox = Box.createHorizontalBox();
	JLabel integrationLabel = new JLabel("融  合  力    ");
	JTextField integration = new JTextField(5);//融合力
	private Box eloquenceHorizontalBox = Box.createHorizontalBox();
	JLabel eloquenceLabel = new JLabel("口        才    ");
	JTextField eloquence = new JTextField(5);//口才
	private Box experienceHorizontalBox = Box.createHorizontalBox();
	JLabel experienceLabel = new JLabel("经  验  值    ");
	JTextField experience = new JTextField(5);//经验值
	private Box allMagicHorizontalBox = Box.createHorizontalBox();	
	JButton setAllMagicButton = new JButton("魔   法   强   化");
	private Box swordLearningHorizontalBox = Box.createHorizontalBox();
	JLabel swordLearningLabel = new JLabel("短兵成长    ");
	JTextField swordLearning = new JTextField(5);//短成长
	private Box pikeLearningHorizontalBox = Box.createHorizontalBox();
	JLabel pikeLearningLabel = new JLabel("长兵成长    ");
	JTextField pikeLearning = new JTextField(5);//长成长
	private Box bowLearningHorizontalBox = Box.createHorizontalBox();
	JLabel bowLearningLabel = new JLabel("弓箭成长    ");
	JTextField bowLearning = new JTextField(5);//弓成长
	private Box magicLearningHorizontalBox = Box.createHorizontalBox();
	JLabel magicLearningLabel = new JLabel("魔法成长    ");
	JTextField magicLearning = new JTextField(5);//魔成长
	
	/*articlesPanel放在mainFrame的EAST，包含equipmentsPanel、itemsPanel*/
	JPanel articlesPanel = new JPanel();
	JPanel equipmentsPanel = new JPanel();
	private Box equipmentsVerticalBox = Box.createVerticalBox();
	private Box helmetHorizontalBox = Box.createHorizontalBox();
	JLabel helmetLabel = new JLabel("头    盔    ");//index+83
	JComboBox helmet = new JComboBox(new String[]{"<空>","软木盔","海盗头巾","铜盔","赤铁盔","法帽","斑斓蛇皮盔","狂力神盔","浴血红盔","夜色之盔","役风之盔","双翼之盔","玳瑁盔","龙鱼盔","银月之盔","黄金神盔","火神盔","水晶盔","梦幻之盔","恶灵之盔","圣者之盔"});//头盔
	private Box clothesHorizontalBox = Box.createHorizontalBox();
	JLabel clothesLabel = new JLabel("衣    服    ");//index+103
	JComboBox clothes = new JComboBox(new String[]{"<空>","制服","软木甲","蛇木甲","硬皮甲","法袍","锁金甲","大地之甲","浴血铠甲","魔幻之袍","骑士铠甲","重盾甲","玳瑁铠甲","龙鱼铠甲","龙鳞铠甲","银圣甲","蜘蛛魔甲","魔导铠甲","冰色之甲","铁蚕黑甲","不死鸟之甲"});//衣服
	private Box weaponHorizontalBox = Box.createHorizontalBox();
	JLabel weaponLabel = new JLabel("武    器    ");//index+0
	JComboBox weapon = new JComboBox(new String[]{"<空>","木剑","铁剑","短斧","双刃长剑","残月弯刀","流苏剑","银线宝剑","狼击剑","玄铁剑","仁者之剑","红刃弯刀","珍珠宝刀","鬼头刀","星落剑","狼牙棒","巨人之斧","龙鱼长刺剑","天使剑","骑士剑","寒极弯刀","依格剑","狂狼宝刀","斩龙刀","死神之剑","梦幻神剑","奥克罗之魂","木枪","铁叉","长钩","铁枪","斧枪","加长型狼牙棒","极速鱼叉","珍珠长棍","星落枪","寒冰锥","无影枪","羊脂白玉棍","黑暗神矛","大力天使杵","银狼枪","暴怒猛虎枪","破龙枪","风神长矛","梦幻神枪","黄金爆裂枪","木弓","铁胎弓","古藤弓","贝壳弓","狼牙弓","龙飞弓","焰尾弓","魔云弓","连弩","珍珠弓","强力弓","十字弓","银箭弓","骑士弓","单刃强击弓","激风神弓","龙爪弓","梦幻十字弓","极品虎鲨弓","诗人强弩","蛇木魔杖","黑暗森林","双子星","琥珀之梦","龙降地动","魔石杖","星月争辉","金色马尔斯","天界之门","希望之星","黄金之神","天地无垠","浩瀚寰宇","精灵幻梦","撼天震地","梦幻大地","神谕","极地之光"});//武器
	private Box shoesHorizontalBox = Box.createHorizontalBox();
	JLabel shoesLabel = new JLabel("鞋    子    ");//index+123
	JComboBox shoes = new JComboBox(new String[]{"<空>","草靴","软木靴","斑斓蛇皮靴","极韧之靴","僧靴","鹿皮长靴","赤虎皮靴","苍狼皮靴","热浪长靴","踏浪靴","骑士靴","天使靴","鹅绒靴","龙鱼皮靴","疾风靴","圣光靴","练金靴","幻影之靴","铁蚕靴","雪女之靴"});//鞋子
	private Box ornamentHorizontalBox = Box.createHorizontalBox();
	JLabel ornamentLabel = new JLabel("饰    品    ");//index+143
	JComboBox ornament = new JComboBox(new String[]{"<空>","恶魔之戒","龙之戒","女皇之戒","精灵之戒","红宝石戒指","蓝宝石戒指","金刚石戒指","软玉戒指","七彩手环","黄金手环","光珠手环","百毒手环","悟性手环","幸运手环","不死鸟胸章","黑猫胸章","骷髅胸章","天空胸章","仙人掌胸章","木雕护符","心之纹章","星之纹章","天之纹章","光之纹章","风之纹章","火之纹章","岩之纹章","冰之纹章","水之纹章","暗之纹章","魔笛项链","祝福项链","友谊项链","大蒜项链","天使项链"});//饰品
	
	JPanel itemsPanel = new JPanel();
	private Box itemsVerticalBox = Box.createVerticalBox();
	private Box itemHorizontalBoxA = Box.createHorizontalBox();
	JLabel itemLabelA = new JLabel("物品一    ");//index+238
	JComboBox itemA = new JComboBox(articlesList.toArray());//物品一
	private Box itemHorizontalBoxB = Box.createHorizontalBox();
	JLabel itemLabelB = new JLabel("物品二    ");//index+238
	JComboBox itemB = new JComboBox(articlesList.toArray());//物品二
	private Box itemHorizontalBoxC = Box.createHorizontalBox();
	JLabel itemLabelC = new JLabel("物品三    ");//index+238
	JComboBox itemC = new JComboBox(articlesList.toArray());//物品三
	private Box itemHorizontalBoxD = Box.createHorizontalBox();
	JLabel itemLabelD = new JLabel("物品四    ");//index+238
	JComboBox itemD = new JComboBox(articlesList.toArray());//物品四
	private Box itemHorizontalBoxE = Box.createHorizontalBox();
	JLabel itemLabelE = new JLabel("物品五    ");//index+238
	JComboBox itemE = new JComboBox(articlesList.toArray());//物品五
	private Box itemHorizontalBoxF = Box.createHorizontalBox();
	JLabel itemLabelF = new JLabel("物品六    ");//index+238
	JComboBox itemF = new JComboBox(articlesList.toArray());//物品六
	private Box itemHorizontalBoxG = Box.createHorizontalBox();
	JLabel itemLabelG = new JLabel("物品七    ");//index+238
	JComboBox itemG = new JComboBox(articlesList.toArray());//物品七
	private Box itemHorizontalBoxH = Box.createHorizontalBox();
	JLabel itemLabelH = new JLabel("物品八    ");//index+238
	JComboBox itemH = new JComboBox(articlesList.toArray());//物品八
	
	
	/*saveButton放在j的SOUTH*/
	JButton saveButton = new JButton("保        存");
	

	
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
		fileFilter.setDescription("存档文件(*.sav)");
		fileChooser.setFileFilter(fileFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		openButton.addActionListener(new ActionListener(){

//			@Override
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showDialog(mainFrame, "打开存档文件");
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
	
	
	
	/*读取存档文件*/
	private boolean loadArchiveFile(String filename){
		if(filename==null){
			JOptionPane.showMessageDialog(mainFrame, "存档文件名为空", "存档文件错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if("".equals(filename.trim())){
			JOptionPane.showMessageDialog(mainFrame, "存档文件名为空", "存档文件错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		RandomAccessFile saveFile = null;
		try {
				saveFile = new RandomAccessFile(filename,"rw");

				/*读取金钱*/
				saveFile.seek(addressMap.get("money"));
				byte[] moneyAmount = new byte[4];	
				saveFile.read(moneyAmount);
				moneyText.setText(new Integer(changeToIntA(moneyAmount)).toString());
				
				/*读取角色。存放角色名列表，每个角色名字占8个字节*/
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
				JOptionPane.showMessageDialog(mainFrame, "存档文件不存在", "存档文件错误", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(mainFrame, "读取存档文件出错", "存档文件错误", JOptionPane.ERROR_MESSAGE);
				return false;
			}finally{
				if(saveFile!=null){
					try {
						saveFile.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(mainFrame, "关闭存档文件出错，请关闭修改器后重新运行", "存档文件错误", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		return true;
	}
	
	
	/*学会所有魔法*/
	private boolean setAllMagic(String filename, int roleIndex){
		//200种魔法，从物品栏后开始
		RandomAccessFile saveFile = null;
		if(filename==null){
			JOptionPane.showMessageDialog(mainFrame, "存档文件名为空", "存档文件错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if("".equals(filename.trim())){
			JOptionPane.showMessageDialog(mainFrame, "存档文件名为空", "存档文件错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(roleIndex==-1){
			JOptionPane.showMessageDialog(mainFrame, "请读取存档文件并选择一个角色", "角色错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try{
				saveFile = new RandomAccessFile(filename,"rw");		
				/*设置魔法的经验值为0x0300*/
				byte[] b = new byte[2];
				b[0]=0;
				b[1]=3;
				for(int i=0;i<200;i++){
					saveFile.seek(addressMap.get("magic")+roleIndex*addressMap.get("distanceBetweenRoles")+i*2);
					saveFile.write(b);
				}
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(mainFrame, "存档文件不存在", "存档文件错误", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(mainFrame, "读取存档文件出错", "存档文件错误", JOptionPane.ERROR_MESSAGE);
				return false;
			}finally{
				if(saveFile!=null){
					try {
						saveFile.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(mainFrame, "关闭存档文件出错，请关闭修改器后重新运行", "存档文件错误", JOptionPane.WARNING_MESSAGE);
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
	
	/*读取金钱，角色属性及装备、物品*/
	private boolean getRoleAttributeAndArticles(String filename, int roleIndex){
		// HP、MP、ATT、DEF、MAG、短兵、长兵、弓箭是0xFFFFFFFF格式
		//经验、SPD、幸运、融合力、口才是0x00000000格式
		//HP、MP、ATT、DEF、MAG、短兵、长兵、弓箭、SPD最大值999
		//幸运、融合力、口才最大值99
		//经验最大值999999
		RandomAccessFile saveFile = null;
		if(filename==null){
			JOptionPane.showMessageDialog(mainFrame, "存档文件名为空", "存档文件错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if("".equals(filename.trim())){
			JOptionPane.showMessageDialog(mainFrame, "存档文件名为空", "存档文件错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(roleIndex==-1){
			JOptionPane.showMessageDialog(mainFrame, "请读取存档文件并选择一个角色", "角色错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		try {
				saveFile = new RandomAccessFile(filename,"rw");
				
				/*4字节属性和装备、物品*/
				byte[] b = new byte[4];
				
				/*金钱*/
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
				JOptionPane.showMessageDialog(mainFrame, "存档文件不存在", "存档文件错误", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(mainFrame, "读取存档文件出错", "存档文件错误", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (SecurityException e) {
				JOptionPane.showMessageDialog(mainFrame, "请联系作者", "方法错误", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(mainFrame, "请联系作者", "方法错误", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (NoSuchMethodException e) {
				JOptionPane.showMessageDialog(mainFrame, "请联系作者", "方法错误", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (IllegalAccessException e) {
				JOptionPane.showMessageDialog(mainFrame, "请联系作者", "方法错误", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (InvocationTargetException e) {
				JOptionPane.showMessageDialog(mainFrame, "请联系作者", "方法错误", JOptionPane.ERROR_MESSAGE);
				return false;
			}finally{
				if(saveFile!=null){
					try {
						saveFile.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(mainFrame, "关闭存档文件出错，请关闭修改器后重新运行", "存档文件错误", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		return true;
	}
	
	/*保存金钱，角色属性及装备物品*/
	private boolean setRoleAttributeAndArticles(String filename, int roleIndex){
		// HP、MP、ATT、DEF、MAG、短兵、长兵、弓箭是0xFFFFFFFF格式
		//经验、SPD、幸运、融合力、口才是0x00000000格式
		//HP、MP、ATT、DEF、MAG、短兵、长兵、弓箭、SPD最大值999
		//幸运、融合力、口才最大值99
		//经验最大值999999
		RandomAccessFile saveFile = null;
		if(filename==null){
			JOptionPane.showMessageDialog(mainFrame, "存档文件名为空", "存档文件错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if("".equals(filename.trim())){
			JOptionPane.showMessageDialog(mainFrame, "存档文件名为空", "存档文件错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(roleIndex==-1){
			JOptionPane.showMessageDialog(mainFrame, "请读取存档文件并选择一个角色", "角色错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try{
				
				if(checkvalues()){
					saveFile = new RandomAccessFile(filename,"rw");
					
					/*4字节属性和装备、物品*/
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
					sb.append("请确保所有输入项均为整数：\n");
					sb.append("1.生命值、魔法值、物攻、防御、魔攻、短兵、长兵、弓箭、速度在0-999之间\n");
					sb.append("2.幸运、融合力、口才、短兵成长、长兵成长、弓箭成长、魔法成长在0-99之间\n");
					sb.append("3.经验值、金钱在0-999999之间\n");
					JOptionPane.showMessageDialog(mainFrame, sb, "输入数据非法", JOptionPane.ERROR_MESSAGE);
				}
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(mainFrame, "存档文件不存在", "存档文件错误", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(mainFrame, "读取存档文件出错", "存档文件错误", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (SecurityException e) {
				JOptionPane.showMessageDialog(mainFrame, "请联系作者", "方法错误", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(mainFrame, "请联系作者", "方法错误", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (NoSuchMethodException e) {
				JOptionPane.showMessageDialog(mainFrame, "请联系作者", "方法错误", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (IllegalAccessException e) {
				JOptionPane.showMessageDialog(mainFrame, "请联系作者", "方法错误", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (InvocationTargetException e) {
				JOptionPane.showMessageDialog(mainFrame, "请联系作者", "方法错误", JOptionPane.ERROR_MESSAGE);
				return false;
			}finally{
				if(saveFile!=null){
					try {
						saveFile.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(mainFrame, "关闭存档文件出错，请关闭修改器后重新运行", "存档文件错误", JOptionPane.WARNING_MESSAGE);
					}
				}
			}	
		return true;
	}
	
	/*0xFFFFFFFF转换*/
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
	
	
	/*0x00000000转换*/
	private void changeToBytesB(int value, byte[] b){
		b[0] = (byte)(value&0x000000FF);
		b[1] = (byte)((value&0x0000FF00)>>>8);
		b[2] = (byte)((value&0x00FF0000)>>>16);
		b[3] = (byte)((value&0xFF000000)>>>24);
	}
	
	/*0xFFFFFFFF转换*/
	private int changeToIntA(byte[] b){
		/*int b0 = 256-(b[0]&0x000000FF);
		int b1 = ((~b[1])&0x000000FF);
		int b2 = ((~b[2])&0x000000FF);
		int b3 = ((~b[3])&0x000000FF);
		return ((b3*256+b2)*256+b1)*256+b0;*/
		return -((b[0]&0x000000FF)|((b[1]&0x000000FF)<<8)|((b[2]&0x000000FF)<<16)|((b[3]&0x000000FF)<<24));
	}
	
	/*0x00000000转换*/
	private int changeToIntB(byte[] b){
		int b0 = (b[0]&0x000000FF);
		int b1 = (b[1]&0x000000FF);
		int b2 = (b[2]&0x000000FF);
		int b3 = (b[3]&0x000000FF);
		return ((b3*256+b2)*256+b1)*256+b0;
	}
	
	/*检查输入属性值的合法性*/
	private boolean checkvalues(){
		// HP、MP、ATT、DEF、MAG、短兵、长兵、弓箭、金钱是0xFFFFFFFF格式
		//经验、SPD、幸运、融合力、口才是0x00000000格式
		//HP、MP、ATT、DEF、MAG、短兵、长兵、弓箭、SPD最大值999
		//幸运、融合力、口才最大值99
		//金钱、经验最大值999999
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
	
	/*存档文件类型过滤器类*/
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
	
/********************************************************以下代码做初始化********************************************************/
	/*界面布局初始化*/
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
		rolesPanel.setBorder(new TitledBorder(new EtchedBorder(),"角色"));
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
		attributesPanel.setBorder(new TitledBorder(new EtchedBorder(),"属性"));
		
		
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
		equipmentsPanel.setBorder(new TitledBorder(new EtchedBorder(),"装备"));
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
		itemsPanel.setBorder(new TitledBorder(new EtchedBorder(),"物品"));
		articlesPanel.add(itemsPanel);
		
		/*saveButton*/
		saveButton.setFont(new Font("猪体",Font.BOLD,20));
		
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
	
	
	/*各种物品名称初始化*/
	private static void articlesListInit(){
		articlesList.clear();
		articlesList.add("<空>");
		articlesList.add("木剑");
		articlesList.add("铁剑");
		articlesList.add("短斧");
		articlesList.add("双刃长剑");
		articlesList.add("残月弯刀");
		articlesList.add("流苏剑");
		articlesList.add("银线宝剑");
		articlesList.add("狼击剑");
		articlesList.add("玄铁剑");
		articlesList.add("仁者之剑");
		articlesList.add("红刃弯刀");
		articlesList.add("珍珠宝刀");
		articlesList.add("鬼头刀");
		articlesList.add("星落剑");
		articlesList.add("狼牙棒");
		articlesList.add("巨人之斧");
		articlesList.add("龙鱼长刺剑");
		articlesList.add("天使剑");
		articlesList.add("骑士剑");
		articlesList.add("寒极弯刀");
		articlesList.add("依格剑");
		articlesList.add("狂狼宝刀");
		articlesList.add("斩龙刀");
		articlesList.add("死神之剑");
		articlesList.add("梦幻神剑");
		articlesList.add("奥克罗之魂");
		articlesList.add("木枪");
		articlesList.add("铁叉");
		articlesList.add("长钩");
		articlesList.add("铁枪");
		articlesList.add("斧枪");
		articlesList.add("加长型狼牙棒");
		articlesList.add("极速鱼叉");
		articlesList.add("珍珠长棍");
		articlesList.add("星落枪");
		articlesList.add("寒冰锥");
		articlesList.add("无影枪");
		articlesList.add("羊脂白玉棍");
		articlesList.add("黑暗神矛");
		articlesList.add("大力天使杵");
		articlesList.add("银狼枪");
		articlesList.add("暴怒猛虎枪");
		articlesList.add("破龙枪");
		articlesList.add("风神长矛");
		articlesList.add("梦幻神枪");
		articlesList.add("黄金爆裂枪");
		articlesList.add("木弓");
		articlesList.add("铁胎弓");
		articlesList.add("古藤弓");
		articlesList.add("贝壳弓");
		articlesList.add("狼牙弓");
		articlesList.add("龙飞弓");
		articlesList.add("焰尾弓");
		articlesList.add("魔云弓");
		articlesList.add("连弩");
		articlesList.add("珍珠弓");
		articlesList.add("强力弓");
		articlesList.add("十字弓");
		articlesList.add("银箭弓");
		articlesList.add("骑士弓");
		articlesList.add("单刃强击弓");
		articlesList.add("激风神弓");
		articlesList.add("龙爪弓");
		articlesList.add("梦幻十字弓");
		articlesList.add("极品虎鲨弓");
		articlesList.add("诗人强弩");
		articlesList.add("蛇木魔杖");
		articlesList.add("黑暗森林");
		articlesList.add("双子星");
		articlesList.add("琥珀之梦");
		articlesList.add("龙降地动");
		articlesList.add("魔石杖");
		articlesList.add("星月争辉");
		articlesList.add("金色马尔斯");
		articlesList.add("天界之门");
		articlesList.add("希望之星");
		articlesList.add("黄金之神");
		articlesList.add("天地无垠");
		articlesList.add("浩瀚寰宇");
		articlesList.add("精灵幻梦");
		articlesList.add("撼天震地");
		articlesList.add("梦幻大地");
		articlesList.add("神谕");
		articlesList.add("极地之光");
		articlesList.add("软木盔");
		articlesList.add("海盗头巾");
		articlesList.add("铜盔");
		articlesList.add("赤铁盔");
		articlesList.add("法帽");
		articlesList.add("斑斓蛇皮盔");
		articlesList.add("狂力神盔");
		articlesList.add("浴血红盔");
		articlesList.add("夜色之盔");
		articlesList.add("役风之盔");
		articlesList.add("双翼之盔");
		articlesList.add("玳瑁盔");
		articlesList.add("龙鱼盔");
		articlesList.add("银月之盔");
		articlesList.add("黄金神盔");
		articlesList.add("火神盔");
		articlesList.add("水晶盔");
		articlesList.add("梦幻之盔");
		articlesList.add("恶灵之盔");
		articlesList.add("圣者之盔");
		articlesList.add("制服");
		articlesList.add("软木甲");
		articlesList.add("蛇木甲");
		articlesList.add("硬皮甲");
		articlesList.add("法袍");
		articlesList.add("锁金甲");
		articlesList.add("大地之甲");
		articlesList.add("浴血铠甲");
		articlesList.add("魔幻之袍");
		articlesList.add("骑士铠甲");
		articlesList.add("重盾甲");
		articlesList.add("玳瑁铠甲");
		articlesList.add("龙鱼铠甲");
		articlesList.add("龙鳞铠甲");
		articlesList.add("银圣甲");
		articlesList.add("蜘蛛魔甲");
		articlesList.add("魔导铠甲");
		articlesList.add("冰色之甲");
		articlesList.add("铁蚕黑甲");
		articlesList.add("不死鸟之甲");
		articlesList.add("草靴");
		articlesList.add("软木靴");
		articlesList.add("斑斓蛇皮靴");
		articlesList.add("极韧之靴");
		articlesList.add("僧靴");
		articlesList.add("鹿皮长靴");
		articlesList.add("赤虎皮靴");
		articlesList.add("苍狼皮靴");
		articlesList.add("热浪长靴");
		articlesList.add("踏浪靴");
		articlesList.add("骑士靴");
		articlesList.add("天使靴");
		articlesList.add("鹅绒靴");
		articlesList.add("龙鱼皮靴");
		articlesList.add("疾风靴");
		articlesList.add("圣光靴");
		articlesList.add("练金靴");
		articlesList.add("幻影之靴");
		articlesList.add("铁蚕靴");
		articlesList.add("雪女之靴");
		articlesList.add("恶魔之戒");
		articlesList.add("龙之戒");
		articlesList.add("女皇之戒");
		articlesList.add("精灵之戒");
		articlesList.add("红宝石戒指");
		articlesList.add("蓝宝石戒指");
		articlesList.add("金刚石戒指");
		articlesList.add("软玉戒指");
		articlesList.add("七彩手环");
		articlesList.add("黄金手环");
		articlesList.add("光珠手环");
		articlesList.add("百毒手环");
		articlesList.add("悟性手环");
		articlesList.add("幸运手环");
		articlesList.add("不死鸟胸章");
		articlesList.add("黑猫胸章");
		articlesList.add("骷髅胸章");
		articlesList.add("天空胸章");
		articlesList.add("仙人掌胸章");
		articlesList.add("木雕护符");
		articlesList.add("心之纹章");
		articlesList.add("星之纹章");
		articlesList.add("天之纹章");
		articlesList.add("光之纹章");
		articlesList.add("风之纹章");
		articlesList.add("火之纹章");
		articlesList.add("岩之纹章");
		articlesList.add("冰之纹章");
		articlesList.add("水之纹章");
		articlesList.add("暗之纹章");
		articlesList.add("魔笛项链");
		articlesList.add("祝福项链");
		articlesList.add("友谊项链");
		articlesList.add("大蒜项链");
		articlesList.add("天使项链");
		articlesList.add("基础火焰术卷轴");
		articlesList.add("修持火焰术卷轴");
		articlesList.add("强化火焰术卷轴");
		articlesList.add("标准火焰术卷轴");
		articlesList.add("璀璨火焰术卷轴");
		articlesList.add("初级炎爆术卷轴");
		articlesList.add("次级炎爆术卷轴");
		articlesList.add("中级炎爆术卷轴");
		articlesList.add("基础冰冻术卷轴");
		articlesList.add("修持冰冻术卷轴");
		articlesList.add("强化冰冻术卷轴");
		articlesList.add("标准冰冻术卷轴");
		articlesList.add("璀璨冰冻术卷轴");
		articlesList.add("初级冰雪术卷轴");
		articlesList.add("次级冰雪术卷轴");
		articlesList.add("中级冰雪术卷轴");
		articlesList.add("基础击风术卷轴");
		articlesList.add("修持击风术卷轴");
		articlesList.add("强化击风术卷轴");
		articlesList.add("标准击风术卷轴");
		articlesList.add("璀璨击风术卷轴");
		articlesList.add("初级旋风术卷轴");
		articlesList.add("次级旋风术卷轴");
		articlesList.add("中级旋风术卷轴");
		articlesList.add("基础魔岩术卷轴");
		articlesList.add("修持魔岩术卷轴");
		articlesList.add("强化魔岩术卷轴");
		articlesList.add("标准魔岩术卷轴");
		articlesList.add("璀璨魔岩术卷轴");
		articlesList.add("初级熔岩术卷轴");
		articlesList.add("次级熔岩术卷轴");
		articlesList.add("中级熔岩术卷轴");
		articlesList.add("基础陨石术卷轴");
		articlesList.add("修持陨石术卷轴");
		articlesList.add("强化陨石术卷轴");
		articlesList.add("标准陨石术卷轴");
		articlesList.add("璀璨陨石术卷轴");
		articlesList.add("初级流星雨卷轴");
		articlesList.add("次级流星雨卷轴");
		articlesList.add("中级流星雨卷轴");
		articlesList.add("基础雷击术卷轴");
		articlesList.add("修持雷击术卷轴");
		articlesList.add("强化雷击术卷轴");
		articlesList.add("标准雷击术卷轴");
		articlesList.add("璀璨雷击术卷轴");
		articlesList.add("初级击破术卷轴");
		articlesList.add("次级击破术卷轴");
		articlesList.add("中级击破术卷轴");
		articlesList.add("长效瘫痪术卷轴");
		articlesList.add("强制瘫痪术卷轴");
		articlesList.add("长效中毒术卷轴");
		articlesList.add("强制中毒术卷轴");
		articlesList.add("长效混乱术卷轴");
		articlesList.add("强制混乱术卷轴");
		articlesList.add("长效昏迷术卷轴");
		articlesList.add("强制昏迷术卷轴");
		articlesList.add("移动术卷轴");
		articlesList.add("解毒术卷轴");
		articlesList.add("回神术卷轴");
		articlesList.add("唤醒术卷轴");
		articlesList.add("马尾草");
		articlesList.add("红浆果");
		articlesList.add("雪山昙花");
		articlesList.add("生命之水");
		articlesList.add("火凤凰的蛋");
		articlesList.add("奥克罗之光");
		articlesList.add("罂粟种子面包");
		articlesList.add("猫眼石");
		articlesList.add("紫色水晶石");
		articlesList.add("初恋少女之泪");
		articlesList.add("三头龙的心");
		articlesList.add("黑暗之灵");
		articlesList.add("妖精之蜜");
		articlesList.add("沙漠甘露");
		articlesList.add("蜜枣瓜");
		articlesList.add("迷蝶香");
		articlesList.add("朱里安的饼乾");
		articlesList.add("血腥玛莉");
		articlesList.add("美人鱼的尾巴");
		articlesList.add("阿罗果酱");
		articlesList.add("精灵之梦");
		articlesList.add("黄金神殿圣水");
		articlesList.add("星石之箭");
		articlesList.add("虾壳汤");
		articlesList.add("蝎尾粉");
		articlesList.add("巫婆的头发");
		articlesList.add("恶魔的诅咒");
		articlesList.add("化功石");
		articlesList.add("鸵鸟精");
		articlesList.add("人偶之幡");
		articlesList.add("逆转风车");
		articlesList.add("七彩蘑菇");
		articlesList.add("天使之泪");
		articlesList.add("龙逆珠");
		articlesList.add("鬼灯");
		articlesList.add("超浓缩大蒜精");
		articlesList.add("天堂之花");
		articlesList.add("雪之羽");
		articlesList.add("唤兽石");
		articlesList.add("绿晶石");
		articlesList.add("院长夫人的画像");
		articlesList.add("利爪猫");
		articlesList.add("猫之药草");
		articlesList.add("灵犬");
		articlesList.add("强力钓竿");
		articlesList.add("洋娃娃");
		articlesList.add("女神花瓶");
		articlesList.add("琉璃杯");
		articlesList.add("银之烛台");
		articlesList.add("魔笛");
		articlesList.add("魔法师宝典");
		articlesList.add("奇诺瓦藏宝图");
		articlesList.add("打火刀");
		articlesList.add("水壶");
		articlesList.add("魔力水晶球");
		articlesList.add("木刻小人偶");
		articlesList.add("基础火焰术");
		articlesList.add("修持火焰术");
		articlesList.add("金强化火焰术");
		articlesList.add("金豪华火焰术");
		articlesList.add("金梦幻火焰术");
		articlesList.add("初级炎爆术");
		articlesList.add("基础冰冻术");
		articlesList.add("修持冰冻术");
		articlesList.add("金强化冰冻术");
		articlesList.add("金豪华冰冻术");
		articlesList.add("金梦幻冰冻术");
		articlesList.add("初级冰雪术");
		articlesList.add("基础击风术");
		articlesList.add("修持击风术");
		articlesList.add("金强化击风术");
		articlesList.add("金豪华击风术");
		articlesList.add("金梦幻击风术");
		articlesList.add("初级旋风术");
		articlesList.add("基础魔岩术");
		articlesList.add("修持魔岩术");
		articlesList.add("金强化魔岩术");
		articlesList.add("金豪华魔岩术");
		articlesList.add("金梦幻魔岩术");
		articlesList.add("初级熔岩术");
		articlesList.add("基础陨石术");
		articlesList.add("修持陨石术");
		articlesList.add("金强化陨石术");
		articlesList.add("金豪华陨石术");
		articlesList.add("金梦幻陨石术");
		articlesList.add("初级流星术");
		articlesList.add("基础雷击术");
		articlesList.add("修持雷击术");
		articlesList.add("金强化雷击术");
		articlesList.add("金豪华雷击术");
		articlesList.add("金梦幻雷击术");
		articlesList.add("初级击破术");
		articlesList.add("基础瘫痪术");
		articlesList.add("长效瘫痪术");
		articlesList.add("强制瘫痪术");
		articlesList.add("修持瘫痪术");
		articlesList.add("群击瘫痪术");
		articlesList.add("基础中毒术");
		articlesList.add("长效中毒术");
		articlesList.add("强制中毒术");
		articlesList.add("修持中毒术");
		articlesList.add("群击中毒术");
		articlesList.add("基础混乱术");
		articlesList.add("长效混乱术");
		articlesList.add("强制混乱术");
		articlesList.add("修持混乱术");
		articlesList.add("群击混乱术");
		articlesList.add("基础昏迷术");
		articlesList.add("长效昏迷术");
		articlesList.add("强制昏迷术");
		articlesList.add("修持昏迷术");
		articlesList.add("群击昏迷术");
		articlesList.add("移动术");
		articlesList.add("群体移动术");
		articlesList.add("解毒术");
		articlesList.add("群体解毒术");
		articlesList.add("回神术");
		articlesList.add("群体回神术");
		articlesList.add("唤醒术");
		articlesList.add("群体唤醒术");
		articlesList.add("小感化术");
		articlesList.add("中感化术");
		articlesList.add("小自疗术");
		articlesList.add("中自疗术");
		articlesList.add("大自疗术");
		articlesList.add("小医疗术");
		articlesList.add("中医疗术");
		articlesList.add("大医疗术");
		articlesList.add("小光疗术");
		articlesList.add("中光疗术");
		articlesList.add("小血疗术");
		articlesList.add("中血疗术");
		articlesList.add("小长魄术");
		articlesList.add("中长魄术");
		articlesList.add("小重疗术");
		articlesList.add("小击魄术");
		articlesList.add("中击魄术");
		articlesList.add("气魄术");
		articlesList.add("小长盾术");
		articlesList.add("中长盾术");
		articlesList.add("中重疗术");
		articlesList.add("小重盾术");
		articlesList.add("中重盾术");
		articlesList.add("盾甲术");
		articlesList.add("大重疗术");
		articlesList.add("小长行术");
		articlesList.add("中长行术");
		articlesList.add("神行术");
		articlesList.add("小急行术");
		articlesList.add("中急行术");
	}
	
	/*各种地址初始化*/
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
		/*以下是物品编码的偏移量*/
		addressMap.put("weaponIndex", -1);
		addressMap.put("helmetIndex", 83);
		addressMap.put("clothesIndex", 103);
		addressMap.put("shoesIndex", 123);
		addressMap.put("ornamentIndex", 143);
		addressMap.put("itemIndex", 238);
	}
	
	/*各种物品编码初始化*/
	private void articlesMapInit(){
		articlesMap.clear();
		int value = -1;
		for(String s:articlesList){
			articlesMap.put(s, value);
			value++;
		}
	}
}
