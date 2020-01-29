package com.example.a22housexam2.DataManager

import androidx.databinding.ObservableArrayList
import androidx.room.Room
import com.example.a22housexam2.ConstantInterface
import com.example.a22housexam2.ViewAdapter.ViewItem.PcInfo_FullItem
import com.example.a22housexam2.ViewAdapter.ViewItem.Room_item

object RoomListManager {

    var roomObserver = ArrayList<ConstantInterface.View_HomeFragment>()
    var roomInfoObserver = HashMap<String,ConstantInterface.View_RoomInfoActivity>()

    public var RoomHashMap = HashMap<String?,ObservableArrayList<PcInfo_FullItem>>()
    public var RoomCardViewList = ObservableArrayList<Room_item>()

    fun attachRoomInfoObserver(view : ConstantInterface.View_RoomInfoActivity){
        var roomNumber = view.getRoomNumber()
        roomInfoObserver.put(roomNumber,view)
    }

    fun attachRoomObserver(view : ConstantInterface.View_HomeFragment) {
        this.roomObserver.add(view)
    }
    
    fun dettachRoomObserver(view : ConstantInterface.View_HomeFragment){
        this.roomObserver.clear()
    }

    fun dettachRoomInfoObserver(){
        this.roomInfoObserver.clear()
    }

    fun addPcItem(item : PcInfo_FullItem){
        var cid : String = item.classId.toString()
        if(!this.RoomHashMap.containsKey(cid)){
            // 없던 room 추가
            var list = ObservableArrayList<PcInfo_FullItem>()
            list.add(item)
            this.RoomHashMap.put(cid,list)
            var roomItem = Room_item(cid)
            addRoom(roomItem)
        }else{
            // 있던 room item 추가
            this.RoomHashMap[cid]!!.add(item)
            notifyRoomInfoObservers(cid)
        }
    }

    fun addRoom(item : Room_item){
        this.RoomCardViewList.add(item)
        notifyRoomObservers()
    }

    fun notifyRoomObservers(){
        for ( observer in roomObserver){
            observer.notifyChanged()
        }
    }

    fun notifyRoomInfoObservers(key  : String){
        if(roomInfoObserver.isEmpty())return
        else roomInfoObserver[key]!!.notifyChanged()
    }

}

