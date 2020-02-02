package com.example.a22housexam2.DataManager

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.room.Room
import com.example.a22housexam2.ConstantInterface
import com.example.a22housexam2.ViewAdapter.ViewItem.PcInfo_FullItem
import com.example.a22housexam2.ViewAdapter.ViewItem.Room_item
import okhttp3.internal.notify

object RoomListManager {

    var roomObserver = ArrayList<ConstantInterface.View_HomeFragment>()
    var roomInfoObserver = HashMap<String,ConstantInterface.View_RoomInfoActivity>()
    var PcInfoObserver = HashMap<String,ConstantInterface.View_PcInfoActivity>()

    public var PcList = HashMap<String?, PcInfo_FullItem>()

    public var RoomHashMap = HashMap<String?,ObservableArrayList<PcInfo_FullItem>>()

    public var RoomHashMapTest = HashMap<String?,HashMap<String?,PcInfo_FullItem>>()

    public var RoomCardViewList = ObservableArrayList<Room_item>()

    fun attachRoomInfoObserver(view : ConstantInterface.View_RoomInfoActivity){
        var roomNumber = view.getRoomNumber()
        roomInfoObserver.put(roomNumber,view)
    }

    fun attachRoomObserver(view : ConstantInterface.View_HomeFragment) {
        this.roomObserver.add(view)
    }

    fun attachPcInfoObserver(view : ConstantInterface.View_PcInfoActivity){
        var pc_id = view.getPcId()
        this.PcInfoObserver.put(pc_id,view)
    }

    fun dettachRoomObserver(view : ConstantInterface.View_HomeFragment){
        this.roomObserver.clear()
    }

    fun dettachRoomInfoObserver(){
        this.roomInfoObserver.clear()
    }

    fun dettachPcInfoObserver(){
        this.PcInfoObserver.clear()
    }

    fun addPcItemTest(item : PcInfo_FullItem){
        var cid : String = item.classId.toString()
        if(!this.RoomHashMap.containsKey(cid)){
            Log.d("Data 관리","존재하지 않는 Room의 PC가 식별되었습니다.")

            // 없던 room 추가
            var list = ObservableArrayList<PcInfo_FullItem>()
            list.add(item)
            var testMap = HashMap<String?, PcInfo_FullItem>()
            testMap.put(item.id,item)
            this.RoomHashMap.put(cid,list)
            this.RoomHashMapTest.put(cid,testMap)
            var roomItem = Room_item(cid)
            addRoom(roomItem)
        }else{
            // 있던 room item 추가
            if(this.RoomHashMapTest[cid]!!.containsKey(item.id)){
                // 존재하는 pc
                if(!this.RoomHashMapTest[cid]!![item.id]!!.equals(item)){
                    Log.d("Data 관리","존재하는 PC가 식별되었습니다.")
                    // 일치 하지 않을 때 -> 수정된 정보가 존재할 때
                    this.RoomHashMapTest[cid]!![item.id] = item
                    // 보조용 리스트에 일단 대입
                    var idx = 0
                    for(pc in RoomHashMap[cid]!!){
                        if(pc.id!!.equals(item.id)){
                            break;
                        }
                        idx +=1
                    }
                    RoomHashMap[cid]!!.removeAt(idx)
                    RoomHashMap[cid]!!.add(0,item)
                    if(PcInfoObserver.containsKey(item.id)){
                        notifyPcInfoObservers(item.id!!)
                    }
                }
                else return
            }
            else{
                // 존재하지 않는 pc 추가된것
                Log.d("Data 관리","존재하지 않는 PC가 식별되었습니다.")
                this.RoomHashMapTest[cid]!!.put(item.id,item)
                this.RoomHashMap[cid]!!.add(item)
            }
            notifyRoomInfoObservers(cid)
        }
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

    fun notifyPcInfoObservers(key : String){
        PcInfoObserver[key]!!.notifyChanged()
    }

}

