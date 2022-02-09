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

class _MyHomePageState extends State<MyHomePage> {
  late MethodChannel methodChannel;
  late EventChannel eventChannel;
  String _img = "";
  String androidSend = "";
  @override
  void initState() {
    super.initState();
    methodChannel = new MethodChannel("myDemo");
    eventChannel = new EventChannel("myEvent");
    eventChannel.receiveBroadcastStream().listen((event) {
      setState(() {
        androidSend = event;
        print(666666);
        print(event);
        _img = event;
      });
    });
    methodChannel.invokeMethod("sendPhoto");
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
              child: RaisedButton(
                color: Colors.yellow,
                onPressed: () {
                  openCamera(context);
                },
              ),
            ),
            Container(
              height: 200,
              width: double.infinity,
              child: Image.file(File(_img)),
            ),
            Container(
              width: double.infinity,
              margin: EdgeInsets.only(left: 39, right: 39),
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
                "$androidSend",
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

  openCamera(BuildContext context) async {
    XFile? image = await ImagePicker().pickImage(source: ImageSource.gallery);
    if (image != null) {
      setState(() {
        _img = image.path;
      });
      print(111111);
      print(image.path);
    }
  }
}
