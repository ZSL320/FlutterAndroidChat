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
      print(call);
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
        body: Container(
          child: AndroidView(
            viewType: "view_zsl",
          ),
        ));
  }
}
