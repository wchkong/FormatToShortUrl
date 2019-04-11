# FormatToShortUrl
一个简单的短网址服务

接口设计：（http://localhost:8080）
1.生成短链：
  GET请求：
   path：/format
   参数：
   long_url= xxxx //长链接
   short_url= //自定义短链接，可不填
   type= auto//类型：自定义-self/系统生成-auto）
   返回值：
   {
    "code": 200,
    "msg": "成功！",
    "data": "http://localhost:8080/D1Qwtrd"
   }

 2. 访问计数
    GET请求：
    path：/count
    参数：
    short_url= //要查询的短链
    返回值：
    {
    "code": 200,
    "msg": "成功",
    "data": 1
    }

不完善的地方：
  1.不能区分长链，会出现重复生成短链的问题
  2.测试用例不完善
