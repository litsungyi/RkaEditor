#pragma once

#include <string>
#include <vector>
#include <Kafka\Stream\ISerializable.h>
#include "Data/Common.h"

namespace Kafka
{
	struct IOutputStream;
	struct IInputStream;
} // namespace Kafka

namespace Rka
{
	class ManData : public Kafka::ISerializable
	{
	public:
		ManData(ManId id);
		~ManData() = default;

	public:
		virtual void WriteToStream(std::shared_ptr<Kafka::IOutputStream> stream);
		virtual void ReadFromStream(std::shared_ptr<Kafka::IInputStream> stream);

		void Print();
		std::string GetName() const { return name; }
		void Upgrade();

	private:
		static Int32 Invert(Int32 input);
		static int AddToValue(int origin, int addValue, int maxValue);

	private:
		static constexpr size_t ManOffset = 0x02E4;
		static constexpr size_t MaxEquipMagic = 10; 
		static constexpr size_t MaxItem = 8;
		static constexpr size_t MaxMagic = 200;

		ManId id;
		std::string name; // 姓名
		int lv; // 等級
		int exp; // 經驗值
		int hp; // 生命力 1-999 0xFF
		int mp; // 魔法力 1-999 0xFF
		int str; // 力量 1-999 0xFF
		int con; // 體質 1-999 0xFF
		int magic; // 魔力 1-999 0xFF
		int speed; // 速度 1-999 0x00
		int lucky; // 幸運 1-99 0x00
		int sword; // 短兵器 1-999 0xFF
		int pike; // 長兵器 1-999 0xFF
		int bow; // 弓箭 1-999 0xFF
		int swordStudy; // 短兵器 1-999 0xFF
		int pikeStudy; // 長兵器 1-999 0xFF
		int bowStudy; // 弓箭 1-999 0xFF
		int magicStudy; // 魔力 1-999 0xFF
		int integration; // 融合力 1-99 0x00
		int eloquence; // 口才 1-99 0x00
		ItemId head;
		ItemId body;
		ItemId shoes;
		ItemId jewelry;
		ItemId weapon;
		std::vector<ItemId> items;
		std::vector<Int16> magics;
		std::vector<MagicId> equipMagics;
	};
} // namespace Rka
 