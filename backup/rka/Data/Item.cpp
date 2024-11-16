#include "Pch.h"

#include <iostream>
#include <Kafka/Stream/IInputStream.h>
#include <Kafka/Stream/IOutputStream.h>
#include "Data/Item.h"

namespace Rka
{
    ItemData::ItemData(ItemId id)
        : id(id)
    {}

    void ItemData::WriteToStream(std::shared_ptr<Kafka::IOutputStream> stream)
    {
    }

    void ItemData::ReadFromStream(std::shared_ptr<Kafka::IInputStream> stream)
    {
        auto offset = static_cast<size_t>(id.Id) * ItemOffset;
        stream->SeekRead(offset);

        name = stream->ReadString(16);
    }
} // namespace Rka
