let boardService = (function(){	
	function getList(param,callback){
		$.getJSON(`/board/getList?pageNum=${param.pageNum}&amount=${param.amount}`, function(data){
			callback(data);
		}
		).fail(function(shr,status, err){});
	}
	return {getList:getList};
})();








