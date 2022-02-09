import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class AndroidViewDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return AndroidViewState();
  }
}

class AndroidViewState extends State<AndroidViewDemo> {
  late MethodChannel methodChannel;
  @override
  void initState() {
    super.initState();
    methodChannel = new MethodChannel("AndroidView");
    methodChannel.setMethodCallHandler(androidCall);
  }

  String? androidSend;
  Future<dynamic> androidCall(MethodCall call) async {
    if (call.method == "androidSend") {
      setState(() {
        androidSend = call.arguments["send"];
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          centerTitle: true,
          title: Text(
            "AndroidView",
            style: TextStyle(fontSize: 15, color: Colors.white),
          ),
        ),
        body: Column(
          children: [
            Container(
                height: 300,
                width: double.infinity,
                color: Colors.lightBlue,
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Container(
                      margin:EdgeInsets.only(top: 6),
                      child: Text(
                        "这里是flutter页面",
                        style: TextStyle(fontSize: 16, color: Colors.white),
                      ),
                    ),
                    androidSend != null
                        ? Container(
                            child: Text(
                              "这是Android端传过来的值:$androidSend",
                              style:
                                  TextStyle(fontSize: 16, color: Colors.white),
                            ),
                          )
                        : Container(),
                    Container(
                      height: 39,
                      margin: EdgeInsets.only(top: 22),
                      color: Colors.lightBlue,
                      child: RaisedButton(
                        onPressed: () {
                          methodChannel.invokeMethod("flutterSend");
                        },
                        child: Text(
                          "点击",
                          style: TextStyle(fontSize: 16, color: Colors.black),
                        ),
                        shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(6)),
                      ),
                    )
                  ],
                )),
            Container(
              height: 390,
              width: double.infinity,
              child: AndroidView(
                viewType: 'myAndroidView',
              ),
            )
          ],
        ));
  }
}
