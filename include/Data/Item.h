#pragma once

#include <string>
#include <Kafka\Stream\ISerializable.h>
#include "Data/Common.h"

namespace Kafka
{
	struct IOutputStream;
	struct IInputStream;
} // namespace Kafka

namespace Rka
{
	class ItemData : public Kafka::ISerializable
	{
	public:
		ItemData(ItemId id);
		~ItemData() = default;

	public:
		virtual void WriteToStream(std::shared_ptr<Kafka::IOutputStream> stream);
		virtual void ReadFromStream(std::shared_ptr<Kafka::IInputStream> stream);

		std::string GetName() const { return name; }

	private:
		static constexpr size_t ItemOffset = 0x0150;

		ItemId id;
		std::string name;
	};
} // namespace Rka
