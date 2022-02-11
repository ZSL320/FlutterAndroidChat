import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:image_picker/image_picker.dart';

import 'androidView.dart';
import 'dart:io';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'FlutterAndroid通信',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'FlutterAndroid通信'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key? key, required this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> with WidgetsBindingObserver {
  late MethodChannel methodChannel;
  late EventChannel eventChannel;
  late MethodChannel sendPhotoToFlutter = new MethodChannel("sendPhotoToFlutter");
  String? _img;
  String? androidSend;
  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance!.addObserver(this);
    methodChannel = new MethodChannel("myDemo");
    eventChannel = new EventChannel("myEvent");
    eventChannel.receiveBroadcastStream().listen((event) {
      setState(() {
        androidSend = event["message"];
        _img = event["path"];
      });
    });
    sendPhotoToFlutter.invokeMethod("sendPhoto");
    methodChannel.invokeMethod("sendPhotoToFlutter");
  }

  @override
  void didChangeDependencies() {
    // TODO: implement didChangeDependencies
    super.didChangeDependencies();
    print("didChangeDependencies");
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    // TODO: implement didChangeAppLifecycleState
    super.didChangeAppLifecycleState(state);
    print("didChangeAppLifecycleState");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Container(
              width: double.infinity,
              margin: EdgeInsets.only(left: 39, right: 39, bottom: 12),
              child: RaisedButton(
                color: Colors.lightBlue,
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12)),
                onPressed: () {
                  methodChannel.invokeMethod("upLoadPhoto");
                  openGallery(context);
                },
                child: Text(
                  "上传图片",
                  style: TextStyle(fontSize: 15, color: Colors.white),
                ),
              ),
            ),
            _img != null
                ? Container(
                    height: 200,
                    width: double.infinity,
                    child: Image.file(File(_img!)),
                  )
                : Container(),
            Container(
              width: double.infinity,
              margin: EdgeInsets.only(left: 39, right: 39, top: 12),
              child: RaisedButton(
                onPressed: () {
                  methodChannel.invokeMethod("toast");
                },
                color: Colors.lightBlue,
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12)),
                child: Text(
                  "点击按钮",
                  style: TextStyle(fontSize: 15, color: Colors.white),
                ),
              ),
            ),
            Container(
              width: double.infinity,
              margin: EdgeInsets.only(left: 39, right: 39, top: 39),
              child: RaisedButton(
                onPressed: () {
                  methodChannel.invokeMethod("sendToFlutter");
                },
                color: Colors.lightBlue,
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12)),
                child: Text(
                  "点击发送",
                  style: TextStyle(fontSize: 15, color: Colors.white),
                ),
              ),
            ),
            Container(
              child: Text(
                androidSend != null ? "$androidSend" : "",
                style: TextStyle(fontSize: 16, color: Colors.black),
              ),
            ),
            Container(
              width: double.infinity,
              margin: EdgeInsets.only(left: 39, right: 39, top: 39),
              child: RaisedButton(
                color: Colors.lightBlue,
                child: Text(
                  "AndroidView",
                  style: TextStyle(fontSize: 15, color: Colors.white),
                ),
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12)),
                onPressed: () {
                  Navigator.push(context, MaterialPageRoute(builder: (context) {
                    return AndroidViewDemo();
                  }));
                },
              ),
            )
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          methodChannel.invokeMethod("open");
        },
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }

  openGallery(BuildContext context) async {
    XFile? image = await ImagePicker().pickImage(source: ImageSource.gallery);
    if (image != null) {
      setState(() {
        _img = image.path;
      });
    }
  }
}
