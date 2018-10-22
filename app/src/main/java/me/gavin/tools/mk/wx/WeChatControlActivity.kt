package me.gavin.tools.mk.wx

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.wechat_control_activity.*
import me.gavin.tools.mk.MonitorSysReceiver
import me.gavin.tools.mk.R

class WeChatControlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wechat_control_activity)
        initView()

        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED)
        registerReceiver(MonitorSysReceiver(), intentFilter)
    }

    private fun initView() {
        ed_group_name.setText(Hawk.get(Constant.GROUP_NAME, ""))
        cb_add_friends.isChecked = Hawk.get(Constant.ADD_FRIENDS, false)
        cb_friends_square.isChecked = Hawk.get(Constant.FRIEND_SQUARE, false)
        cb_catch_red_packet.isChecked = Hawk.get(Constant.RED_PACKET, false)
        val members = Hawk.get(Constant.MEMBER_LIST, mutableListOf<String>())
        if (members.size > 0) {
            val sb = StringBuilder()
            for (member in members) {
                sb.append(member).append("\n")
            }
            ed_friends.setText(sb.toString())
        }
        btn_sure.setOnClickListener {
            Hawk.put(Constant.GROUP_NAME, ed_group_name.text.toString())
            shortToast("群聊名称已保存！")
        }
        btn_clear.setOnClickListener {
            Hawk.put(Constant.GROUP_NAME, "")
            shortToast("群聊名称已清除！")
            ed_group_name.setText("")
        }
        btn_write.setOnClickListener {
            val memberList = (ed_friends.text.toString()).split("\n").filter { it.trim() != "" }
            Hawk.put(Constant.MEMBER_LIST, memberList)
            Log.e("Test", memberList.toString())
            shortToast("数据写入成功！")
        }
        btn_reset.setOnClickListener {
            Hawk.put(Constant.MEMBER_LIST, mutableListOf<String>())
            ed_friends.setText("")
            shortToast("数据重置成功！")
        }
        btn_open_wechat.setOnClickListener {
            val intent = packageManager.getLaunchIntentForPackage("com.tencent.mm")
            startActivity(intent)
        }
        btn_open_accessbility.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
        cb_add_friends.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) Hawk.put(Constant.ADD_FRIENDS, true) else Hawk.put(Constant.ADD_FRIENDS, false)
        }
        cb_friends_square.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) Hawk.put(Constant.FRIEND_SQUARE, true) else Hawk.put(Constant.FRIEND_SQUARE, false)
        }
        cb_catch_red_packet.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) Hawk.put(Constant.RED_PACKET, true) else Hawk.put(Constant.RED_PACKET, false)
        }
    }
}
