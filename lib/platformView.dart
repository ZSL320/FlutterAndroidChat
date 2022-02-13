import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class MyPlatformView extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return MyPlatformViewState();
  }
}

class MyPlatformViewState extends State<MyPlatformView> {
   MethodChannel methodChannel = new MethodChannel("viewPlatform");
  @override
  void initState() {
    super.initState();
    methodChannel.setMethodCallHandler(callBack);
  }

  Future<dynamic> callBack(MethodCall call) async {
    setState(() {
      print(1111111);
      print(call.method);
      print(call.arguments);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          centerTitle: true,
          title: Text(
            "platformView",
            style: TextStyle(fontSize: 16),
          ),
        ),
        body:Column(
          children: [
            Container(
              height: 390,
              child: AndroidView(
                viewType: "view_zsl",
              ),
            ),
            Container(
              height: 39,
              width: double.infinity,
              margin: EdgeInsets.symmetric(horizontal: 22,vertical: 66),
              child: RaisedButton(
                onPressed: (){
                  methodChannel.invokeMethod("sendMessage","你好，我是来自flutter的消息");
                },
                color: Colors.lightBlue,
                child: Text("发送至Android"),
                shape:RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12)
                ),
              ),
            )
          ],
        )

     );
  }
}
