#pragma once

namespace Rka
{
	enum class ManId
	{
		Allen = 0, // 亞倫
		Nia = 1, // 妮雅
		Fiona = 2, // 菲歐娜
		Reysen = 3, // 雷森
		Pop =4, // 波普
		Emily = 5, // 艾蜜莉
		Rogers = 6, // 羅傑斯
		Will = 7, // 威爾
		Linda = 8, // 琳達
		Barbara = 9, // 芭芭拉
		WillJr = 10, // 小威
		Gould = 11, // 古爾德 (教官)
		Feynman = 12, // 費曼 (教官)
		Bernard = 13, // 柏那德 (會叛變)
		Takuya = 14, // 木村達也
		Jill = 15, // 吉兒
		Crick = 16, // 克里克 (會叛變)
		Gates = 17, // 蓋茲 (敵人)
		Moorhead = 18, // 穆爾黑德 (敵人)
		Sandy = 19, // 珊蒂雅 (敵人)
		Sani = 20, // 薩尼 (敵人)
		Gros = 21, // 葛羅 (敵人)
		Molehu = 22, // 莫雷夫 (敵人)
		Reid = 23, // 列特 (敵人)
		Buck = 24, // 霸克 (敵人)
		Shaye = 25, // 莎耶
	};

	struct ItemId
	{
		int Id;
	};

	struct MagicId
	{
		int Id;
	};
}
