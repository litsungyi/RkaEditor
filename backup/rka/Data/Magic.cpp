#include "Pch.h"

#include <iostream>
#include <Kafka\Stream\IInputStream.h>
#include <Kafka\Stream\IOutputStream.h>
#include "Data\Magic.h"

namespace Rka
{
    MagicData::MagicData(MagicId id)
        : id(id)
    {}

    void MagicData::WriteToStream(std::shared_ptr<Kafka::IOutputStream> stream)
    {
    }

    void MagicData::ReadFromStream(std::shared_ptr<Kafka::IInputStream> stream)
    {
        auto offset = static_cast<size_t>(id.Id) * MagicOffset;
        stream->SeekRead(offset);

        name = stream->ReadString(12);
    }
} // namespace Rka
