... 중략 ...

// 가격계산
function price_calculate1() { // 원소스
    var it_price = parseInt($("form[name='fitem'] input#it_price").val());

    if (isNaN(it_price))
        return;

    var $el_prc = $("form[name='fitem'] input.io_price");
    var $el_qty = $("form[name='fitem'] input[name^=ct_qty]");
    var $el_type = $("form[name='fitem'] input[name^=io_type]");
    var $el_id = $("form[name='fitem'] input[name^=io_id]");
    var price, type, qty,id,
        check = true,
        total = 0;
    $el_prc.each(function (index) {
        price = parseInt($(this).val());
        qty = parseInt($el_qty.eq(index).val());
        type = $el_type.eq(index).val();
        id = $el_id.eq(index).val();
        if(type=="0"&&id.length==0){
            check = false;
            total += qty;
        }else{
            total += price * qty;
        }
        // if (type == "0") { // 선택옵션
        //     total += (it_price + price) * qty;
        // } else { // 추가옵션
        //     total += price * qty;
        // }
        
    });
    if (check){
        total = total + it_price;
    }else{
        total = total * it_price;
    }
    

    $("#sit_tot_price").empty().html("<span>총 금액</span> KRW " + number_format(String(total)));
    $("#cart_sit_tot_price").empty().html("<span>총 금액</span> KRW " + number_format(String(total)));
    //$("#sit_tot_price2").empty().html("KRW " + number_format(String(total)));
}
function price_calculate2() {//수정소스
    var it_price = parseInt($("form[name='fitem'] input#it_price").val());

    if (isNaN(it_price))
        return;

    var $el_prc = $("form[name='fitem'] input.io_price");
    var $el_qty = $("form[name='fitem'] input[name^=ct_qty]");
    var $el_type = $("form[name='fitem'] input[name^=io_type]");
    var $el_id = $("form[name='fitem'] input[name^=io_id]");
    var price, type, qty,id,
        check = true,
        total = 0;
    $el_prc.each(function (index) {
        price = parseInt($(this).val());
        qty = parseInt($el_qty.eq(index).val());
        type = $el_type.eq(index).val();
        id = $el_id.eq(index).val();
        //if(type=="0"&&id.length==0){
        if(type=="0"){
            check = false;
            total += qty;
        }else{
            total += price * qty;
        }
        // if (type == "0") { // 선택옵션
        //     total += (it_price + price) * qty;
        // } else { // 추가옵션
        //     total += price * qty;
        // }
        
    });
    if (check){
        total = total + it_price;
    }else{
        total = total * (it_price+price);
    }
    
    $("#sit_tot_price").empty().html("<span>총 금액</span> KRW " + number_format(String(total))+"  "+String(qty));
    $("#cart_sit_tot_price").empty().html("<span>총 금액1</span> KRW " + number_format(String(total)));
    //$("#sit_tot_price2").empty().html("KRW " + number_format(String(total)));
}
function price_calculate()
{ // 그누 원래 소스
    /* 2018-09-05 */
    var it_price = parseInt($("#it_price").val());
    // var it_price = parseInt($("input#it_price").val());

    if(isNaN(it_price))
        return;
    /* 2018-09-05 */
    var $el_prc = $("#sit_sel_option>ul>li>input.io_price");
    var $el_qty = $("input[name^=ct_qty]");
    var $el_type = $("input[name^=io_type]");
    var $el_id = $("form[name='fitem'] input[name^=io_id]");
    var price, type, qty, total = 0, check = true;

    /* 2018-09-05 */
    /*$el_prc.each(function(index) {
        price = parseInt($(this).val());
        qty = parseInt($el_qty.eq(index).val());
        type = $el_type.eq(index).val();

        if(type == "0") { // 선택옵션
            total += (it_price + price) * qty;
        } else { // 추가옵션
            total += price * qty;
        }
    });*/
    /* 2018-09-05 */
    $el_prc.each(function (index) {
        price = parseInt($(this).val());
        qty = parseInt($el_qty.eq(index).val());
        type = $el_type.eq(index).val();
        id = $el_id.eq(index).val();

        if(type=="0"&&id.length==0){
            check = false;
        }else{
            total += price * qty;
        }

        if(type == "0") { // 선택옵션 : 수량만 변화
            total += it_price * qty;
        }
        
    });

    /* 2018-09-05 */
    $("#sit_tot_price").empty().html("KRW "+number_format(String(total))+"원");
    $("#sit_tot_price2").empty().html("KRW "+number_format(String(total))+"원");
    $("#sit_tot_price3").empty().html("KRW "+number_format(String(total))+"원");
}

// php chr() 대응
function chr(code) {
    return String.fromCharCode(code);
}