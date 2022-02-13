import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class GaoMapView extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return GaoMapViewState();
  }
}

class GaoMapViewState extends State<GaoMapView> with AutomaticKeepAliveClientMixin{

  @override
  bool get wantKeepAlive => true;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text("高德地图View"),
      ),
      body: AndroidView(
        viewType: 'GaoDeMapView',
      ),
    );
  }
}
