let login = (event) => {
	event.preventDefault();
	let jsonString = {
			'userName': $('input[name=userName]').val(),
			'password': $('input[name=password]').val()
	};
	
	$.ajax({
		type: 'POST',
		url: '/ecsite/api/login',
		data: JSON.stringify(jsonString),
		contentType: 'application/json',
		datatype: 'json',
		scriptCharset: 'utf-8'
	})
	
	.then((result) => {
			let user = JSON.parse(result);
			$('#welcome').text(` -- ようこそ！ ${user.fullName} さん`);
			$('#hiddenUserId').val(user.id);
			$('input[name=userName]').val('');
			$('input[name=password]').val('');
		}, () => {
			console.error('Error: ajax connection failed.');
		}
	);
};

let addCart = (event) => {
	let tdList = $(event.target).parent().parent().find('td');
	
	let id = $(tdList[0]).text();
	let goodsName = $(tdList[1]).text();
	let price = $(tdList[2]).text();
	let count = $(tdList[3]).find('input').val();
	
	if (count === '0' || count === ''){
		alert('注文数が０または空欄です。')
		return;
	}
	
	let cart = {
		'id': id,
		'goodsName': goodsName,
		'price': price,
		'count': count
	};
	cartList.push(cart);
	
	let tbody = $('#cart').find('tbody');
	$(tbody).children().remove();
	cartList.forEach(function(cart,index) {
		let tr = $('<tr />');
		
		$('<td />', { 'text': cart.id}).appendTo(tr);
		$('<td />', { 'text': cart.goodsName}).appendTo(tr);
		$('<td />', { 'text': cart.price}).appendTo(tr);
		$('<td />', { 'text': cart.count}).appendTo(tr);
		let tdButton = $('<td />');
		$('<button />', {
			'text': 'カート削除',
			'calss':'removeBtn',
		}).appendTo(tdButton);
		
		$(tdButton).appendTo(tr);
		$(tr).appendTo(tbody);
	});
	$('.removeBtn').on('click', removeCart);
	
};