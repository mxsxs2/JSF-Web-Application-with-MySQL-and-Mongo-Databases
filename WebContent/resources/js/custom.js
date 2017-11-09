$(document).ready(function() {
	// Turn every table into sortable table
	$('table').DataTable();
	
	// Add add country button listener
	$('body').on('click', '#addcountrybutton', function() {
		$("#adddialog").load('add_country.xhtml',function(){
			$("#adddialog").dialog({
				autoOpen: false,
	            resizable: false,
	            modal: true,
	            fluid: true,
	            width: 'auto'}).dialog('open');
		})
	})
});

function checkResponse(data){
	//Check if the ajax was successful
	if (data.status === 'success') {
		//Check if there is an error message
		if($('.ajaxResponseMessage').html().includes("success")){
			//If there is none then reload
			document.location.reload();
		}
	}
}