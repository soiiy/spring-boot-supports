package com.soiiy.mall.module.product.data

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.soiiy.utils.json.Jackson
import java.time.LocalDateTime

class ProductItemResult {

    var id: String = ""

    var itemMenu: String = ""

    var itemMenuLabel: String = ""

    var itemMenuPicUrl: String? = null

    var itemTopic: String? = null

    var itemTopicLabel: String? = null

    var itemTopicPicUrl: String? = null

    // 自定义商品编号
    var customNo: String? = null

    //标题
    var title: String = ""

    //商品详情信息
    var content: String? = null

    //主图
    var firstPicUrl: String? = null

    //商品图片
    var albumPicUrls: String? = null

    //价格
    @JsonSerialize(using = Jackson.Decimal2Serializer::class)
    @JsonDeserialize(using = Jackson.Decimal2Deserializer::class)
    var price: Number = 0

    // 自定义 - 其它价格 - 展示价，会员价
    @JsonSerialize(using = Jackson.Decimal2Serializer::class)
    @JsonDeserialize(using = Jackson.Decimal2Deserializer::class)
    var originalPrice: Number = 0

    // 上架状态 - 销售中、已售罄、已下架
    var sellState: String = "NORMAL"

    // 总库存
    var stockTotal: Int = 0

    // 锁定库存
    var stockLocked: Int = 0

    // 已销售
    var stockSold: Int = 0

    // 剩余库存
    var stockAvailable: Int = 0

    // 购买限制
    var buyQuota: Int = 0

    // 产品规格 颜色：红，黄，蓝，绿 // 尺寸：大、中、小
    var specs: Map<String, List<String>>? = null

    // 排序权重： 默认为1000=1.0
    var rankNum: Int = 1000


    // 扩展标签，可作为查询参数
    var extraTags: String? = null

    // 附加信息
    var extraInfo: Map<String, String> = mapOf()


    // （自动） 上架时间 - 下架时间

    var createdAt: LocalDateTime = LocalDateTime.now()

    var updatedAt: LocalDateTime = LocalDateTime.now()

}