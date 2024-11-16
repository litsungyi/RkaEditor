#pragma once

#include <vector>
#include <memory>
#include <Kafka\Applications\IConsole.h>

namespace Kafka
{
    class InoutFileStream;
} // namespace Kafka

namespace Rka
{
    class Console : public Kafka::IConsole
    {
    public:
        int Run();

    private:
        void LoadMan();
        void LoadMagic();
        void LoadItem();
        bool OpenFile();

        static constexpr int MaxMans = 1292;//52;

        std::vector<std::string> m_mans;
        std::vector<std::string> m_magics;
        std::vector<std::string> m_items;
        std::shared_ptr< Kafka::InoutFileStream > m_stream;
    };
} // namespace Rka
