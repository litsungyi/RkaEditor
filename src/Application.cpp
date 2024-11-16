#include "Pch.h"

#include <iostream>
#include <vector>
#include <cassert>
#include <Kafka/Stream/OutputFileStream.h>
#include <Kafka/Stream/InputFileStream.h>
#include <Kafka/Stream/InoutFileStream.h>
#include "TinyFileDialogs/tinyfiledialogs.h"
#include "Application.h"
#include "Data/Man.h"
#include "Data/Magic.h"
#include "Data/Item.h"

namespace Rka
{
    int Console::Run()
    {
        LoadMan();
        //LoadMagic();
        //LoadItem();
        return 0;

        if ( !OpenFile() )
        {
            std::string title( "錯誤!" );
            std::string message( "開啟檔案失敗" );

            tinyfd_messageBox( title.c_str(), message.c_str(), "ok", "error", 0 );
            return 0;
        }

       auto manDatas = std::vector<ManData>();
        for (auto i = 0; i < 26; ++i)
        {
            if (i <= 10 || i == 14 || i == 15 || i == 25)
            {
                auto man = ManData(static_cast<ManId>(i));
                man.ReadFromStream(m_stream);

                manDatas.push_back(man);
            }
            else if (i == 11) // 古爾德 (教官)
            {

            }
            else if (i == 11) // 費曼 (教官)
            {

            }
        }

        for (auto i = 0; i < manDatas.size(); ++i)
        {
            auto mandata = manDatas[i];
            mandata.Upgrade();
            mandata.WriteToStream(m_stream);
        }

        return 0;
    }

    void Console::LoadMan()
    {
        const char* filters[] =
        {
            "man.dat"
        };
        std::string fileName = tinyfd_openFileDialog("選擇檔案", "", 1, filters, "", 0);
        if (fileName.length() == 0)
        {
            return;
        }

        auto stream = std::shared_ptr< Kafka::InoutFileStream >(new Kafka::InoutFileStream(fileName, true));
        if (stream && stream->CanRead())
        {
            std::cout << "Man" << std::endl;
            for (auto i = 0; i < MaxMans; ++i)
            {
                auto man = ManData(static_cast<ManId>(i));
                man.ReadFromStream(stream);
                man.Print();

                auto name = man.GetName();
                //std::cout << name << std::endl;
                m_mans.push_back(name);
            }
        }
    }

    void Console::LoadMagic()
    {
        const char* filters[] =
        {
            "magic.dat"
        };
        std::string fileName = tinyfd_openFileDialog("選擇檔案", "", 1, filters, "", 0);
        if (fileName.length() == 0)
        {
            return;
        }

        auto stream = std::shared_ptr< Kafka::InoutFileStream >(new Kafka::InoutFileStream(fileName, true));
        if (stream && stream->CanRead())
        {
            std::cout << "Magic" << std::endl;
            for (auto i = 0; i < 200; ++i)
            {
                auto magic = MagicData(static_cast<MagicData>(MagicId{ i }));
                magic.ReadFromStream(stream);

                auto name = magic.GetName();
                std::cout << name << std::endl;
                m_magics.push_back(name);
            }
        }
    }

    void Console::LoadItem()
    {
        const char* filters[] =
        {
            "item.dat"
        };
        std::string fileName = tinyfd_openFileDialog("選擇檔案", "", 1, filters, "", 0);
        if (fileName.length() == 0)
        {
            return;
        }

        auto stream = std::shared_ptr< Kafka::InoutFileStream >(new Kafka::InoutFileStream(fileName, true));
        if (stream && stream->CanRead())
        {
            std::cout << "Item" << std::endl;
            for (auto i = 0; i < 398; ++i)
            {
                auto item = ItemData(static_cast<ItemData>(ItemId{ i }));
                item.ReadFromStream(stream);

                auto name = item.GetName();
                std::cout << name << std::endl;
                m_items.push_back(name);
            }
        }
    }

    bool Console::OpenFile()
    {
        const char* filters[] =
        {
            "man.dat"
        };
        std::string fileName = tinyfd_openFileDialog( "選擇檔案", "", 1, filters, "", 0 );
        if ( fileName.length() == 0 )
        {
            return false;
        }

        m_stream = std::shared_ptr< Kafka::InoutFileStream >( new Kafka::InoutFileStream( fileName, true ) );
        return m_stream && m_stream->CanRead();
    }
} // namespace Rka
