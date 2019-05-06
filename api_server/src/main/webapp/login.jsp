<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>微信支付测试页</title>
</head>
<body>
<input type="button" onclick="submit()" value="提交订单"/>

<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<script>
    function submit() {
        var option = {
            "tradeMethod": "0",
            "rechargeMode": "0",
            "totalFee": "1",
            "tradeFee": "0.01",
            "discount": "0.01",
            "supPrice": "1",
            "supNo": "cs001sb",
            "number": "100",
            "type": "2",
            "priceTypeName": "Q币",
            "priceTypeUnit": "Q币",
            "acctType": "QQ号码",
            "account": "272678772",
            "gameAccount": "",
            "gameArea": "",
            "gameServ": "",
            "appId": "155661958504450303",
            "appName": "腾讯Q币"
        };

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=UTF-8",
            url: "http://xian-api.900sup.com/pay/pay",
            dataType: "json",
            data: JSON.stringify(option),
            success: function (res) {
                console.log(res);
                location.href = res.data;
            },
            error: function (err) {
                console.log(err);
            }
        });
    }
</script>
</body>
</html>
