<html>
<head>
    <meta charset="utf-8">
    <link href="./css/init.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="app">
    <#if orderResponse.hasError()>
        您尝试访问的课程不存在，请从索引页面自动跳转，或者联系管理员

        <#elseif orderResponse.getData().isPaid == false>
        <img src="${orderResponse.getData().getQrcode()}">
        支付完成后请手动刷新页面

        <#else >
        支付成功，请访问。。。。
    </#if>
</div>
</body>
</html>