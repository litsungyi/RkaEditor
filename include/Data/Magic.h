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
	class MagicData : public Kafka::ISerializable
	{
	public:
		MagicData(MagicId id);
		~MagicData() = default;

	public:
		virtual void WriteToStream(std::shared_ptr<Kafka::IOutputStream> stream);
		virtual void ReadFromStream(std::shared_ptr<Kafka::IInputStream> stream);

		std::string GetName() const { return name; }

	private:
		static constexpr size_t MagicOffset = 0x01A0;

		MagicId id;
		std::string name;

	};
} // namespace Rka
