#include "Pch.h"

#include <iostream>
#include <Kafka/Stream/IInputStream.h>
#include <Kafka/Stream/IOutputStream.h>
#include "Data/Man.h"

namespace Rka
{
    ManData::ManData(ManId id)
        : id(id)
        , lv(0)
        , exp(0)
        , hp(0)
        , mp(0)
        , str(0)
        , con(0)
        , magic(0)
        , speed(0)
        , lucky(0)
        , sword(0)
        , pike(0)
        , bow(0)
        , swordStudy(0)
        , pikeStudy(0)
        , bowStudy(0)
        , magicStudy(0)
        , integration(0)
        , eloquence(0)
    {}

    void ManData::WriteToStream(std::shared_ptr<Kafka::IOutputStream> stream)
    {
        auto offset = static_cast<size_t>(id) * ManOffset;
        stream->SeekWrite(offset);

        stream->SkipWrite(2);
        stream->SkipWrite(2);
        stream->WriteInt32(Invert(hp));
        stream->WriteInt32(Invert(mp));
        stream->SkipWrite(40); // equipMagics
        stream->SkipWrite(8); // Name
        stream->SkipWrite(8);
        stream->SkipWrite(4);
        stream->SkipWrite(4);
        stream->SkipWrite(4);
        stream->WriteInt32(Invert(hp));
        stream->WriteInt32(Invert(mp));
        stream->SkipWrite(4); // Exp
        stream->WriteInt32(Invert(str));
        stream->WriteInt32(Invert(con));
        stream->WriteInt32(Invert(magic));
        stream->WriteInt32(speed);
        stream->WriteInt32(lucky);
        stream->SkipWrite(4);
        stream->SkipWrite(4);
        stream->WriteInt32(Invert(sword));
        stream->WriteInt32(Invert(pike));
        stream->WriteInt32(Invert(bow));
        stream->WriteInt32(swordStudy);
        stream->WriteInt32(pikeStudy);
        stream->WriteInt32(bowStudy);
        stream->SkipWrite(4);
        stream->SkipWrite(4);
        stream->SkipWrite(4);
        stream->WriteInt32(magicStudy);
        stream->WriteInt32(integration);
        stream->WriteInt32(eloquence);
        stream->SkipWrite(4);
        stream->SkipWrite(20); // equipment
        stream->SkipWrite(32); // item

        for (auto count = 0; count < MaxMagic; ++count)
        {
            stream->WriteInt16(magics[count]);
        }
    }

    void ManData::ReadFromStream(std::shared_ptr<Kafka::IInputStream> stream)
    {
        auto offset = static_cast<size_t>(id) * ManOffset;
        stream->SeekRead(offset);

        stream->SkipRead(2);
        stream->SkipRead(2);
        hp = Invert(stream->ReadInt32());
        mp = Invert(stream->ReadInt32());
        for (auto i = 0; i < MaxEquipMagic; ++i)
        {
            auto magicId = MagicId{ stream->ReadInt32() };
            equipMagics.push_back(magicId);
        }

        name = stream->ReadString(8);
        
        stream->SkipRead(4);
        stream->SkipRead(4);
        id = static_cast<ManId>(stream->ReadInt32());
        stream->SkipRead(4);
        stream->SkipRead(4);
        auto hpCurrent = Invert(stream->ReadInt32());
        auto mpCurrent = Invert(stream->ReadInt32());
        exp = stream->ReadInt32();
        str = Invert(stream->ReadInt32());
        con = Invert(stream->ReadInt32());
        magic = Invert(stream->ReadInt32());
        speed = stream->ReadInt32();
        lucky = stream->ReadInt32();
        stream->SkipRead(4);
        stream->SkipRead(4);
        sword = Invert(stream->ReadInt32());
        pike = Invert(stream->ReadInt32());
        bow = Invert(stream->ReadInt32());
        swordStudy = stream->ReadInt32();
        pikeStudy = stream->ReadInt32();
        bowStudy = stream->ReadInt32();
        stream->SkipRead(4);
        stream->SkipRead(4);
        stream->SkipRead(4);
        magicStudy = stream->ReadInt32();
        integration = stream->ReadInt32();
        eloquence = stream->ReadInt32();
        stream->SkipRead(4);
        head = ItemId{ stream->ReadInt32() };
        body = ItemId{ stream->ReadInt32() };
        shoes = ItemId{ stream->ReadInt32() };
        jewelry = ItemId{ stream->ReadInt32() };
        weapon = ItemId{ stream->ReadInt32() };

        for (auto count = 0; count < MaxItem; ++count)
        {
            items.push_back(ItemId{ stream->ReadInt32() });
        }

        for (auto count = 0 ; count < MaxMagic; ++count)
        {
            magics.push_back(stream->ReadInt16());
        }
    }

    void ManData::Print()
    {
        std::cout << "#" << static_cast<int>(id) << "\t" << name << "\t" << hp << "\t" << mp << "\t" \
            << str << "\t" << con << "\t" << speed << "\t" << lucky << "\t" \
            << sword << "\t" << pike << "\t" << bow << "\t" << magic << "\t" \
            << swordStudy << "\t" << pikeStudy << "\t" << bowStudy << "\t" << magicStudy << "\t" \
            << integration << "\t" << eloquence << std::endl;
    }

    void ManData::Upgrade()
    {
        hp = AddToValue(hp, 200, 999);
        mp = AddToValue(mp, 100, 999);
        str = AddToValue(str, 100, 999);
        con = AddToValue(con, 100, 999);
        magic = AddToValue(magic, 100 + magicStudy, 999);
        speed = AddToValue(speed, 100, 999);
        lucky = 99;
        sword = AddToValue(sword, 100 + swordStudy, 999);
        pike = AddToValue(pike, 100 + pikeStudy, 999);
        bow = AddToValue(bow, 100 + bowStudy, 999);
        swordStudy = AddToValue(swordStudy, 20, 99);
        pikeStudy = AddToValue(pikeStudy, 20, 99);
        bowStudy = AddToValue(bowStudy, 20, 99);
        magicStudy = AddToValue(magicStudy, 20, 99);
        integration = 99;
        eloquence = 99;

        switch (id)
        {
        case ManId::Barbara:
            for (auto i = 0; i <= 15; ++i)
            {
                magics[i] = 400;
            }
            break;

        case ManId::Fiona:
            for (auto i = 16; i <= 31; ++i)
            {
                magics[i] = 400;
            }
            break;

        case ManId::Jill:
            for (auto i = 32; i <= 47; ++i)
            {
                magics[i] = 400;
            }
            break;

        case ManId::WillJr:
            for (auto i = 48; i <= 63; ++i)
            {
                magics[i] = 400;
            }
            break;

        case ManId::Nia:
            for (auto i = 64; i <= 79; ++i)
            {
                magics[i] = 400;
            }
            break;

        case ManId::Rogers:
            for (auto i = 80; i <= 95; ++i)
            {
                magics[i] = 400;
            }
            break;
        }
    }

    int ManData::AddToValue(int origin, int addValue, int maxValue)
    {
        auto newValue = origin + addValue;
        return newValue > maxValue ? maxValue : newValue;
    }

    Int32 ManData::Invert(Int32 input)
    {
        return -input;
        //return (input ^ 0xFFFFFFFF) + 1;
    }
} // namespace Rka
