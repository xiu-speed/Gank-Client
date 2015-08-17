//
//  AndroidViewController.swift
//  Gank-Client-iOS
//
//  Created by huben on 15/8/16.
//  Copyright (c) 2015年 github. All rights reserved.
//

import UIKit



class AndroidViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    lazy var data = NSMutableData()
    
    var tableView : UITableView?
    var items = ["武汉","上海","北京","深圳","广州","重庆","香港","台海","天津"]
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initView()
        requestData()
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func initView(){
        // 初始化tableView的数据
        self.tableView=UITableView(frame:self.view.frame,style:UITableViewStyle.Plain)
        // 设置tableView的数据源
        self.tableView!.dataSource=self
        // 设置tableView的委托
        self.tableView!.delegate = self
        //
        self.tableView!.registerClass(UITableViewCell.self, forCellReuseIdentifier: "cell")
        self.view.addSubview(self.tableView!)
        
        
    }
    
    func requestData() {

        var requestModel:RequestModel = RequestModel(type:"Android", pageSize: 10,page: 1)
        var urlString = UrlConfigs.BASE_URL + requestModel.dataType + "/" + String(requestModel.pageSize) + "/" + String(requestModel.page)
        var url: NSURL = NSURL(string: UrlConfigs.BASE_URL)!
        NSLog(urlString)
        var request:NSURLRequest = NSURLRequest(URL: url)
        var connection:NSURLConnection = NSURLConnection(request: request, delegate: self, startImmediately: true)!
        
//        Alamofire.request(.GET, urlString, parameters: ["foo": "bar"])
//            .response { request, response, data, error in
//                println(request)
//                println(response)
//                println(error)
        }
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    //总行数
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return self.items.count
    }
    
    //加载数据
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell{
        
        let cell = tableView .dequeueReusableCellWithIdentifier("cell", forIndexPath: indexPath) as! UITableViewCell

        var s = items[indexPath.row]
        cell.textLabel?.text = s
        return cell;
        
    }
    
    
    //选择一行
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath){
        let alert = UIAlertView()
        alert.title = "提示"
        alert.message = "你选择的是\(self.items[indexPath.row])"
        alert.addButtonWithTitle("Ok")
        alert.show()
    }
    
    

}
