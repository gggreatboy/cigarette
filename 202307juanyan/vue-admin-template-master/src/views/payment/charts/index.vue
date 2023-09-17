<template>
    <div>
        <h1>支付统计-可视化界面</h1>
        <div class="charts">
            <div id="first" class="first"></div>
            <div id="second" class="first"></div>
            <div id="third" class="first"></div>
            <div id="forth" class="first"></div>
        </div>
    </div>
</template>

<script>
import { getProfitAll, getProfit, getProfitByStatus, getProfitByWay, getProfitByUserId } from "@/api/payment.js";
import { findAll } from "@/api/vip.js";
import { getToken } from '@/utils/auth';
import * as echarts from 'echarts'
export default {
    data() {
        return {
            dataList1: [],
            xlist: [],
            ylist: [],
            option: {},
            xlist2: [],
            xlist3: [],
            ylist3: [],
            data4: 0
        }
    },
    mounted() {
        getProfitByStatus().then(res => {
            console.log(res.data.data);
            this.dataList1 = res.data.data;
            if(this.dataList1[0]){this.xlist[0] = "支付方式" + this.dataList1[0].pay_status + ":全款支付";}
            if(this.dataList1[1]){this.xlist[1] = "支付方式" + this.dataList1[1].pay_status + ":分期支付";}
            if(this.dataList1[2]){this.xlist[2] = "支付方式" + this.dataList1[2].pay_status + ":货到付款";}
            for (var i = 0; i < this.dataList1.length; i++) {
                this.ylist[i] = this.dataList1[i].s
            }
            console.log("this.xlist1" + this.xlist);
            console.log("this.ylist1" + this.ylist);
            this.initEcharts1();
        }),
            getProfitByWay().then(res2 => {
                console.log(res2.data.data);
                this.dataList2 = res2.data.data;
                for (var i = 0; i < this.dataList2.length; i++) {
                if(this.dataList2[i]){
                    if (this.dataList2[i].pay_way == 1) this.xlist2[0] = this.dataList2[i].s;
                    if (this.dataList2[i].pay_way == 2) this.xlist2[1] = this.dataList2[i].s;
                    if (this.dataList2[i].pay_way == 3) this.xlist2[2] = this.dataList2[i].s;
                    if (this.dataList2[i].pay_way == 4) this.xlist2[3] = this.dataList2[i].s;
                    if (this.dataList2[i].pay_way == 5) this.xlist2[4] = this.dataList2[i].s;
                    if (this.dataList2[i].pay_way == 6) this.xlist2[5] = this.dataList2[i].s;
                    }
                }
                console.log("this.xlist2" + this.xlist2);
                this.initEcharts2();
            }),
            getProfitByUserId().then(res3 => {
                console.log(res3.data.data);
                this.dataList3 = res3.data.data;
                for (var i = 0; i < this.dataList3.length; i++) {
                    if (this.dataList3[i]) {
                        this.ylist3[i] = this.dataList3[i].user_id;
                        this.xlist3[i] = this.dataList3[i].s;
                    }
                }
                this.initEcharts3();
            }),
            getProfitAll().then(res4 => {
                console.log(res4.data.data);
                this.data4 = res4.data.data;
                this.initEcharts4();
            })
    },
    methods: {
        initEcharts1() {
            var chartDom = document.getElementById('first');
            var myChart = echarts.init(chartDom);
            console.log("this.xlist1" + this.xlist);
            console.log("this.ylist1" + this.ylist);

            var option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                title: {
                    text: '不同支付方式的付款金额',   //主标题
                    subtext: '用户支付方式统计',   //副标题
                    textAlign: 'left',    //居左
                    textStyle: {         //样式
                        fontSize: 20
                    }
                },
                grid: {
                    left: '4%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data: this.xlist,
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: '支付金额',
                        type: 'bar',
                        barWidth: '60%',
                        data: this.ylist
                    },
                    {
                        label: { //柱体上显示数值
                            show: true,//开启显示
                            position: 'top',//在上方显示
                            textStyle: {//数值样式
                                fontSize: '7px',
                                color: '#666'
                            },
                            formatter: '{c}',
                        }
                    }
                ]
            };

            option && myChart.setOption(option);
        },
        initEcharts2() {
            var chartDom = document.getElementById('second');
            var myChart = echarts.init(chartDom);
            var option;

            option = {
                tooltip: {
                    trigger: 'item'
                },
                title: {
                    text: '不同支付渠道的付款金额',   //主标题
                    subtext: '用户支付渠道统计',   //副标题
                    textAlign: 'left',    //居左
                    textStyle: {         //样式
                        fontSize: 20
                    }
                },
                legend: {
                    top: '92%',
                    left: 'center',
                },
                series: [
                    {
                        name: 'Access From',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        itemStyle: {
                            borderRadius: 10,
                            borderColor: '#fff',
                            borderWidth: 2
                        },
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: 40,
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: [

                            { value: this.xlist2[0], name: '支付宝' },
                            { value: this.xlist2[1], name: '微信' },
                            { value: this.xlist2[2], name: '银联' },
                            { value: this.xlist2[3], name: 'BTC/ETH' },
                            { value: this.xlist2[4], name: '数字人民币' },
                            { value: this.xlist2[5], name: '现金' },
                        ]
                    }
                ]
            };

            option && myChart.setOption(option);
        },
        initEcharts3() {
            var chartDom = document.getElementById('third');
            var myChart = echarts.init(chartDom);
            var KSMC = this.xlist3;
            var data = this.ylist3;
            for (var i = 0; i < data.length; i++) { data[i] = "id为" + data[i] + "号用户" };
            var ksmc1 = KSMC.slice(0, 3)
            var data1 = data.slice(0, 3)
            var option = {
                color: ['#3398DB'],
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                title: {
                    text: '不同用户的支付总金额',   //主标题
                    subtext: '用户支付金额统计',   //副标题
                    textAlign: 'left',    //居左
                    textStyle: {         //样式
                        fontSize: 20
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'value'
                    }
                ],
                // dataZoom: [//滑动条
                //     {
                //         yAxisIndex: 0,//这里是从X轴的0刻度开始
                //         show: false,//是否显示滑动条，不影响使用
                //         type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
                //         startValue: 0, // 从头开始。
                //         endValue: 6  // 一次性展示6个。
                //     }
                // ],
                yAxis: [
                    {
                        type: 'category',
                        inverse: true, //是否是反向坐标轴
                        data: data1
                    }
                ],
                series: [
                    {
                        name: '用户总消费金额',
                        type: 'bar',
                        barWidth: '60%',
                        data: ksmc1,

                    }

                ]
            };
            let count = 3

            setInterval(function () {

                ksmc1.push(KSMC[count])
                data1.push(data[count])
                data1.shift()
                ksmc1.shift()
                if (count === data.length - 1) {
                    count = -1
                }
                count++
                myChart.setOption(option);
            }, 2000);
        },
        initEcharts4() {
            var chartDom = document.getElementById('forth');
            var myChart = echarts.init(chartDom);
            var angle = 0;//角度，用来做简单的动画效果的
            var value = this.data4;
            var option = {
                backgroundColor: "#174C81",
                title: {
                    text: '{c|' + '当前总销售额' + '}'+'\n'+'{a|' + value + '}'+'\n'+'{c|' + '元' + '}',
                    x: 'center',
                    y: 'center',
                    textStyle: {
                        rich: {
                            a: {
                                fontSize: 54,
                                color: '#29EEF3'
                            },

                            c: {
                                fontSize: 27,
                                color: '#ffffff',
                                padding: [5,0]
                            }
                        }
                    }
                },
                legend: {
                    type: "plain",
                    orient: "vertical",
                    right: 0,
                    top: "10%",
                    align: "auto",
                    data: [{
                        name: '涨价后没吃过',
                        icon: "circle"
                    }, {
                        name: '天天吃',
                        icon: "circle"
                    }, {
                        name: '三五天吃一次',
                        icon: "circle"
                    }, {
                        name: '半个月吃一次',
                        icon: "circle"
                    }],
                    textStyle: {
                        color: "white",
                        fontSize: 16,
                        padding: [10, 1, 10, 0]
                    },
                    selectedMode: false
                },
                series: [{
                    name: "ring5",
                    type: 'custom',
                    coordinateSystem: "none",
                    renderItem: function (params, api) {
                        return {
                            type: 'arc',
                            shape: {
                                cx: api.getWidth() / 2,
                                cy: api.getHeight() / 2,
                                r: Math.min(api.getWidth(), api.getHeight()) / 2 * 0.6,
                                startAngle: (0 + angle) * Math.PI / 180,
                                endAngle: (90 + angle) * Math.PI / 180
                            },
                            style: {
                                stroke: "#0CD3DB",
                                fill: "transparent",
                                lineWidth: 1.5
                            },
                            silent: true
                        };
                    },
                    data: [0]
                }, {
                    name: "ring5",
                    type: 'custom',
                    coordinateSystem: "none",
                    renderItem: function (params, api) {
                        return {
                            type: 'arc',
                            shape: {
                                cx: api.getWidth() / 2,
                                cy: api.getHeight() / 2,
                                r: Math.min(api.getWidth(), api.getHeight()) / 2 * 0.6,
                                startAngle: (180 + angle) * Math.PI / 180,
                                endAngle: (270 + angle) * Math.PI / 180
                            },
                            style: {
                                stroke: "#0CD3DB",
                                fill: "transparent",
                                lineWidth: 1.5
                            },
                            silent: true
                        };
                    },
                    data: [0]
                }, {
                    name: "ring5",
                    type: 'custom',
                    coordinateSystem: "none",
                    renderItem: function (params, api) {
                        return {
                            type: 'arc',
                            shape: {
                                cx: api.getWidth() / 2,
                                cy: api.getHeight() / 2,
                                r: Math.min(api.getWidth(), api.getHeight()) / 2 * 0.65,
                                startAngle: (270 + -angle) * Math.PI / 180,
                                endAngle: (40 + -angle) * Math.PI / 180
                            },
                            style: {
                                stroke: "#0CD3DB",
                                fill: "transparent",
                                lineWidth: 1.5
                            },
                            silent: true
                        };
                    },
                    data: [0]
                }, {
                    name: "ring5",
                    type: 'custom',
                    coordinateSystem: "none",
                    renderItem: function (params, api) {
                        return {
                            type: 'arc',
                            shape: {
                                cx: api.getWidth() / 2,
                                cy: api.getHeight() / 2,
                                r: Math.min(api.getWidth(), api.getHeight()) / 2 * 0.65,
                                startAngle: (90 + -angle) * Math.PI / 180,
                                endAngle: (220 + -angle) * Math.PI / 180
                            },
                            style: {
                                stroke: "#0CD3DB",
                                fill: "transparent",
                                lineWidth: 1.5
                            },
                            silent: true
                        };
                    },
                    data: [0]
                }, {
                    name: "ring5",
                    type: 'custom',
                    coordinateSystem: "none",
                    renderItem: function (params, api) {
                        let x0 = api.getWidth() / 2;
                        let y0 = api.getHeight() / 2;
                        let r = Math.min(api.getWidth(), api.getHeight()) / 2 * 0.65;
                        let point = getCirlPoint(x0, y0, r, (90 + -angle))
                        return {
                            type: 'circle',
                            shape: {
                                cx: point.x,
                                cy: point.y,
                                r: 2
                            },
                            style: {
                                stroke: "#0CD3DB",//粉
                                fill: "#0CD3DB"
                            },
                            silent: true
                        };
                    },
                    data: [0]
                }, {
                    name: "ring5",  //绿点
                    type: 'custom',
                    coordinateSystem: "none",
                    renderItem: function (params, api) {
                        let x0 = api.getWidth() / 2;
                        let y0 = api.getHeight() / 2;
                        let r = Math.min(api.getWidth(), api.getHeight()) / 2 * 0.65;
                        let point = getCirlPoint(x0, y0, r, (270 + -angle))
                        return {
                            type: 'circle',
                            shape: {
                                cx: point.x,
                                cy: point.y,
                                r: 2
                            },
                            style: {
                                stroke: "#0CD3DB",      //绿
                                fill: "#0CD3DB"
                            },
                            silent: true
                        };
                    },
                    data: [0]
                }, {
                    name: '吃猪肉频率',
                    type: 'pie',
                    radius: ['58%', '45%'],
                    silent: true,
                    clockwise: true,
                    startAngle: 90,
                    z: 0,
                    zlevel: 0,
                    label: {
                        normal: {
                            position: "center",
                        }
                    },
                    data: [{
                        value: value,
                        name: "",
                        itemStyle: {
                            normal: {
                                color: { // 完成的圆环的颜色
                                    colorStops: [{
                                        offset: 0,
                                        color: '#4FADFD' // 0% 处的颜色
                                    }, {
                                        offset: 1,
                                        color: '#28E8FA' // 100% 处的颜色
                                    }]
                                },
                            }
                        }
                    },
                    {
                        value: 2758799 - value,
                        name: "",
                        label: {
                            normal: {
                                show: false
                            }
                        },
                        itemStyle: {
                            normal: {
                                color: "#173164"
                            }
                        }
                    }
                    ]
                },

                {
                    name: "",
                    type: "gauge",
                    radius: "58%",
                    center: ['50%', '50%'],
                    startAngle: 0,
                    endAngle: 359.9,
                    splitNumber: 8,
                    hoverAnimation: true,
                    axisTick: {
                        show: false
                    },
                    splitLine: {
                        length: 450,
                        lineStyle: {
                            width: 5,
                            color: "#174C81"
                        }
                    },
                    axisLabel: {
                        show: false
                    },
                    pointer: {
                        show: false
                    },
                    axisLine: {
                        lineStyle: {
                            opacity: 0
                        }
                    },
                    detail: {
                        show: false
                    },
                    data: [{
                        value: 0,
                        name: ""
                    }]
                },

                ]
            };
            //获取圆上面某点的坐标(x0,y0表示坐标，r半径，angle角度)
            function getCirlPoint(x0, y0, r, angle) {
                let x1 = x0 + r * Math.cos(angle * Math.PI / 180)
                let y1 = y0 + r * Math.sin(angle * Math.PI / 180)
                return {
                    x: x1,
                    y: y1
                }
            }
            function draw() {
                angle = angle + 3
                myChart.setOption(option, true)
                //window.requestAnimationFrame(draw);
            }
            setInterval(function () {
                //用setInterval做动画感觉有问题
                draw()
            }, 100);

        }

    }

}
</script>
<style>
h1 {
    text-align: center;
    margin: 20px;
}

.charts {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
}

.first {
    width: 45%;
    height: 40vh;
    border: solid 1px black;
    margin: 20px;
}
</style>