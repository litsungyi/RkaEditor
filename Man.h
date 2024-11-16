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
		std::string name; // �m�W
		int lv; // ����
		int exp; // �g���
		int hp; // �ͩR�O 1-999 0xFF
		int mp; // �]�k�O 1-999 0xFF
		int str; // �O�q 1-999 0xFF
		int con; // ��� 1-999 0xFF
		int magic; // �]�O 1-999 0xFF
		int speed; // �t�� 1-999 0x00
		int lucky; // ���B 1-99 0x00
		int sword; // �u�L�� 1-999 0xFF
		int pike; // ���L�� 1-999 0xFF
		int bow; // �}�b 1-999 0xFF
		int swordStudy; // �u�L�� 1-999 0xFF
		int pikeStudy; // ���L�� 1-999 0xFF
		int bowStudy; // �}�b 1-999 0xFF
		int magicStudy; // �]�O 1-999 0xFF
		int integration; // �ĦX�O 1-99 0x00
		int eloquence; // �f�~ 1-99 0x00
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
 