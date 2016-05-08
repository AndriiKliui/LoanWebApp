function defaultDate(){
    document.getElementById('term').value=new Date().toJSON().slice(0,10);
}

function formSubmit(){
    var loan ={
        amount : $("#amount").val(),
        term : $("#term").val()
    };
    if(0 >= loan.amount){
        alert("Loan amount can't be negative or '0'")
    } else if(new Date()>=new Date(loan.term)){
        alert("Loan date term can't be later current date")
    } else {
        $.ajax({
            method: 'POST',
            url: 'loan',
            contentType: "application/json",
            data: JSON.stringify(loan),
            success: function (data) {
                alert(data);
            },
            error: function (data) {
                alert('Some params was not valid.');
            }
        });
    }
}
