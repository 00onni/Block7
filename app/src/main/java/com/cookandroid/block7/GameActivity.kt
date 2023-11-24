package com.cookandroid.block7

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class Member(
    var name: String,
    var kor_name: String,

    var hungerLevel: Int = 100,
    var thirstLevel: Int = 100,
    var mentalLevel: Int = 100,

    val hungerState : String = "",
    val thirstState : String = "",
    val mentalState: String = "",

    var isCrazy: Boolean = false,
    var isVeryCrazy: Boolean = false,
    var isHurt: Boolean = false,
    var isVeryHurt: Boolean = false,
    var isSick: Boolean = false,
    var isVerySick: Boolean = false,
    var isTired: Boolean = false,
    var isFatigued: Boolean = false,

    var isAlien: Boolean = false,
    var isBrainwashing: Boolean = false,
    var isAlive : Boolean = true
) {
    // Additional functions and logic can be added here as needed
}

class Item(
    var food : Int = 0,
    var water : Int = 0,
    var axe : Boolean = false,
    var book : Boolean = false,
    var card : Boolean = false,
    var drone : Boolean = false,
    var firstaidkit : Boolean = false,
    var flashlight : Boolean = false,
    var gasmask : Boolean = false,
    var lock : Boolean = false,
    var map : Boolean = false,
    var pesticide : Boolean = false,
    var phone : Boolean = false,
    var rattle : Boolean = false,
    var toolbox : Boolean = false
){
}

class GameActivity : BaseActivity() {

    private lateinit var item_axe : ImageView
    private lateinit var item_book : ImageView
    private lateinit var item_card : ImageView
    private lateinit var item_drone : ImageView
    private lateinit var item_firstaidkit : ImageView
    private lateinit var item_flashlight : ImageView
    private lateinit var item_gasmask : ImageView
    private lateinit var item_lock : ImageView
    private lateinit var item_map : ImageView
    private lateinit var item_pesticide : ImageView
    private lateinit var item_phone : ImageView
    private lateinit var item_rattle : ImageView
    private lateinit var item_toolbox : ImageView

    private lateinit var member_dameun : ImageView
    private lateinit var member_sowoon : ImageView
    private lateinit var member_eunju : ImageView
    private lateinit var member_hyundong : ImageView

    private lateinit var blackBackground : ImageView
    private lateinit var dayText : TextView

    val dameun  = Member(name = "dameun", kor_name = "담은")
    val eunju  = Member(name = "eunju", kor_name = "은주")
    val sowoon  = Member(name = "sowoon", kor_name = "소운")
    val hyundong = Member(name = "hyundong", kor_name = "현동")

    val item =  Item()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)

        item_axe = findViewById(R.id.item_axe)
        item_book = findViewById(R.id.item_book)
        item_card = findViewById(R.id.item_card)
        item_drone = findViewById(R.id.item_drone)
        item_firstaidkit = findViewById(R.id.item_firstaidkit)
        item_flashlight = findViewById(R.id.item_flashlight)
        item_gasmask = findViewById(R.id.item_gasmask)
        item_lock = findViewById(R.id.item_lock)
        item_map = findViewById(R.id.item_map)
        item_pesticide = findViewById(R.id.item_pesticide)
        item_phone = findViewById(R.id.item_phone)
        item_rattle = findViewById(R.id.item_rattle)
        item_toolbox = findViewById(R.id.item_toolbox)

        member_dameun = findViewById(R.id.member_dameun)
        member_eunju = findViewById(R.id.member_eunju)
        member_sowoon = findViewById(R.id.member_sowoon)
        member_hyundong = findViewById(R.id.member_hyundong)

        blackBackground = findViewById(R.id.black_background)
        dayText = findViewById(R.id.day_text)

        item.randomizeItems()

        var date = 0;
        updateItemVisibility(item)

        updateMemberVisibility(dameun, member_dameun)
        updateMemberVisibility(eunju, member_eunju)
        updateMemberVisibility(sowoon, member_sowoon)
        updateMemberVisibility(hyundong, member_hyundong)

        val finishbutton : Button = findViewById(R.id.finish_button)
        finishbutton.setOnClickListener {
            animateScreenTransition(blackBackground, dayText)
        }
    }

    fun updateItemVisibility(item: Item) {
        item_axe.visibility = if (item.axe) View.VISIBLE else View.GONE
        item_book.visibility = if (item.book) View.VISIBLE else View.GONE
        item_card.visibility = if (item.card) View.VISIBLE else View.GONE
        item_drone.visibility = if (item.drone) View.VISIBLE else View.GONE
        item_firstaidkit.visibility = if (item.firstaidkit) View.VISIBLE else View.GONE
        item_flashlight.visibility = if (item.flashlight) View.VISIBLE else View.GONE
        item_gasmask.visibility = if (item.gasmask) View.VISIBLE else View.GONE
        item_lock.visibility = if (item.lock) View.VISIBLE else View.GONE
        item_map.visibility = if (item.map) View.VISIBLE else View.GONE
        item_pesticide.visibility = if (item.pesticide) View.VISIBLE else View.GONE
        item_phone.visibility = if (item.phone) View.VISIBLE else View.GONE
        item_rattle.visibility = if (item.rattle) View.VISIBLE else View.GONE
        item_toolbox.visibility = if (item.toolbox) View.VISIBLE else View.GONE
    }

    fun Item.randomizeItems() {

        this.food = (3..5).random()
        this.water = (3..5).random()

        val fields = Item::class.java.declaredFields.filter { it.type == Boolean::class.javaPrimitiveType }
        fields.forEach { it.isAccessible = true }

        // Reset all items to false
        fields.forEach { it.setBoolean(this, false) }

        // Shuffle the list and take the first 6 fields to set them to true
        fields.shuffled().take(6).forEach { it.setBoolean(this, true) }
    }

    fun updateMemberVisibility(member: Member, memberView: ImageView) {
        memberView.visibility = if (member.isAlive) View.VISIBLE else View.GONE
    }

    fun animateScreenTransition(background: ImageView, dayText: TextView) {
        // 배경의 페이드 인 애니메이션
        ObjectAnimator.ofFloat(background, "alpha", 0f, 1f).apply {
            duration = 1000 // n초 동안
            start()
        }.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // 배경 애니메이션이 끝나면 텍스트의 페이드 인 애니메이션 시작
                ObjectAnimator.ofFloat(dayText, "alpha", 0f, 1f).apply {
                    duration = 500 // 3초 동안
                    start()
                }.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        // 텍스트 애니메이션이 끝나면 2초 동안 유지
                        dayText.postDelayed({
                            // 텍스트의 페이드 아웃 애니메이션
                            ObjectAnimator.ofFloat(dayText, "alpha", 1f, 0f).apply {
                                duration = 2000 // 3초 동안
                                start()
                            }
                            // 배경의 페이드 아웃 애니메이션
                            ObjectAnimator.ofFloat(background, "alpha", 1f, 0f).apply {
                                duration = 2000 // 3초 동안
                                start()
                            }
                        }, 2000) // 2초 동안 대기
                    }
                })
            }
        })
    }
}