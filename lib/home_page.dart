import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'android_back_desktop.dart';
import 'first_page.dart';
import 'map_gaoDe.dart';

class MyHomePage extends StatefulWidget {
  MyHomePage({Key? key, required this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> with WidgetsBindingObserver {
  late PageController _controller;
  int pageIndex = 0;
  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance!.addObserver(this);
    _controller = PageController(
      initialPage: 0,
      keepPage: true,
      viewportFraction: 1,
    );
  }

  Future<bool> _onBackPressed() async {
    AndroidBackTop.backDeskTop(); //设置为返回不退出app
    return false;
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
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
        body: WillPopScope(
          onWillPop: _onBackPressed,
          child: PageView(
            physics: NeverScrollableScrollPhysics(),
            controller: _controller,
            onPageChanged: (index) {
              setState(() {
                pageIndex = index;
              });
            },
            children: [FirstPage(), GaoMapView()],
          ),
        ),
        bottomNavigationBar: BottomNavigationBar(
          currentIndex: pageIndex,
          items: [
            BottomNavigationBarItem(
              title: Text(
                "首页",
                style: TextStyle(
                  fontSize: pageIndex == 0 ? 19 : 16,
                  color: pageIndex == 0 ? Colors.lightBlue : null,
                ),
              ),
              icon: Icon(
                Icons.home,
                size: pageIndex == 0 ? 32 : 22,
                color: pageIndex == 0 ? Colors.lightBlue : null,
              ),
            ),
            BottomNavigationBarItem(
              title: Text(
                "地图",
                style: TextStyle(
                  fontSize: pageIndex == 1 ? 19 : 16,
                  color: pageIndex == 1 ? Colors.lightBlue : null,
                ),
              ),
              icon: Icon(
                Icons.map,
                size: pageIndex == 1 ? 32 : 22,
                color: pageIndex == 1 ? Colors.lightBlue : null,
              ),
            )
          ],
          onTap: (index) {
            setState(() {
              pageIndex = index;
              _controller.jumpToPage(pageIndex);
            });
          },
        ));
  }
}
