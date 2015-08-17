//
//  RequestModel.swift
//  Gank-Client-iOS
//
//  Created by huben on 15/8/17.
//  Copyright (c) 2015年 github. All rights reserved.
//

import UIKit

class RequestModel: NSObject {
   
    var dataType:String
    var pageSize:Int
    var page:Int
    
    init(type:String, pageSize:Int, page:Int) {
        self.dataType = type
        self.pageSize = pageSize
        self.page = page
    }
    
    struct DATATYPE {
        let girl = "福利"
        let android = "Android"
        let ios = "iOS"
        let video = "休息视频"
        let ext_res = "拓展资源"
        let ui = "前端"
        let all = "all"
    }
    
}
