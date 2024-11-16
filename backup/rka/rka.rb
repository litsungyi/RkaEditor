require 'BinData'
require 'yaml'

class RkaEditorHelper
  def self.invert(int)
    puts int
    int ^ 0xFFFFFFFF + 1
  end
end

class MagicIds < BinData::Array
  endian :little

  int32 :magic_id
end

class ItemIds < BinData::Array
  endian :little

  int32 :item_ids
end

class ManData < BinData::Record
  MaxEquipMagic = 10
  MaxEquipment = 5
  MaxItem = 8
  MaxMagic = 200

  endian :little

  uint32 :unknown1
  uint32 :hp
  uint32 :mp
  magic_ids :equip_magics, initial_length: MaxEquipMagic
  string :name, :read_length => 8
  uint64 :unknown2
  uint32 :unknown3
  uint32 :unknown4
  uint32 :unknown5
  uint32 :max_hp
  uint32 :max_mp
  uint32 :exp
  uint32 :str
  uint32 :con
  uint32 :magic
  uint32 :speed
  uint32 :lucky
  uint32 :unknown6
  uint32 :unknown7
  uint32 :sword
  uint32 :pike
  uint32 :bow
  uint32 :swordStudy
  uint32 :pikeStudy
  uint32 :bowStudy
  uint32 :unknown8
  uint32 :unknown9
  uint32 :unknown10
  uint32 :magicStudy
  uint32 :integration
  uint32 :eloquence
  uint32 :unknown11
  item_ids :equipments, initial_length: MaxEquipment
  item_ids :items, initial_length: MaxItem

  def hp
    RkaEditorHelper::invert(:hp)
  end

  def mp
    RkaEditorHelper::invert(:mp)
  end

  def max_hp
    RkaEditorHelper::invert(:max_hp)
  end

  def max_mp
    RkaEditorHelper::invert(:max_mp)
  end

  def str
    RkaEditorHelper::invert(:str)
  end

  def con
    RkaEditorHelper::invert(:con)
  end

  def magic
    RkaEditorHelper::invert(:magic)
  end

  def sword
    RkaEditorHelper::invert(:sword)
  end

  def pike
    RkaEditorHelper::invert(:pike)
  end

  def bow
    RkaEditorHelper::invert(:bow)
  end
end

io = File.open('./man.dat', 'rb')
#io.seek(0x02E4)
man_data = ManData.read(io)
puts man_data.snapshot
puts man_data.max_mp