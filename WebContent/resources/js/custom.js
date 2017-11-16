$(document).ready(function() {
	// Turn every table(except the city search one) into sortable table
	$("table").not(document.getElementById('citysearchtable')).DataTable();
	
	// Add add head of state button listener
	setUpDialogButtonListener("Add Head Of State","add_headofstate.xhtml",'#addheadofstatebutton');
	
	// Add add region button listener
	setUpDialogButtonListener("Add Region","add_region.xhtml",'#addregionbutton');

	// Add add city button listener
	setUpDialogButtonListener("Add City","add_city.xhtml",'#addcitybutton');
	
	// Add add country button listener
	setUpDialogButtonListener("Add Country","add_country.xhtml",'#addcountrybutton');

	// Add update country button listener
	setUpDialogButtonListener("Update Country","update_country.xhtml?code="+$(this).attr("accesskey"),'.updatecountrybutton');

	// Add city details button listener
	setUpDialogButtonListener("City Details","city_details.xhtml?code="+$(this).attr("accesskey"),'.citydetailsbutton');
	
	//Set up every country delete button
	setUpDeleteButtonListeners("Delete Country",'.deletecountrybutton');
	//Set up every head of state delete button
	setUpDeleteButtonListeners("Delete Head Of State",'.deleteheadofstatebutton');
	
	//Add listener to the dialog close event
	$("body").on("dialogclose", "#dialog",function() {
		 //Reset the dialog
	     $( this ).dialog( "destroy" );
	     $( this ).html("");
	} );
});

//Function used to load the dialog content and open it
function setUpDialogButtonListener(title,page,selector){
	// Add button listener
	$('body').on('click', selector, function() {
		//Load the content of the file into a hidden container
		$("#dialog").load(page,function(){
			//When the loading finished convert the container into a dialog box and show it
			$("#dialog").dialog({
				title: title,
				autoOpen: false,
	            resizable: false,
	            modal: true,
	            fluid: true,
	            width: 'auto'}).dialog('open');
		})
	})
}

//Function used to check the jsf ajax response after from the form is sent
function checkResponse(data){
	console.log(data.status);
	//Check if the ajax was successful
	if (data.status === 'success') {
		//Check if there is an error message
		if($('.ajaxResponseMessage').html().includes("success")){
			//If there is none then reload
			document.location.reload();
		}
	}
}


//Function used to set up confirmation dialogs for a give selector
function setUpDeleteButtonListeners(title,selector){
	//Get every country delete button
	$(selector).each(function(){
		//Extract the original onclick action
		var originalClick=$(this).get(0).onclick;
		//Remove original onclick
		$(this).attr("onclick","");
		//Add the new onclick action to the button
		$(this).on('click', function() {
			//Add the confirmation text
			$( "#dialog" ).html("Would you like to delete "+$(this).attr('accesskey')+"?");
			//Convert to a dialog. add the buttons and Open up
			$( "#dialog" ).dialog({
				  dialogClass: "no-close",
				  title: title,
				  buttons: [
				    {
				      text: "Delete",
				      click: function() {
				    	//Run original click
				    	originalClick();
				        $( this ).dialog( "close" );
				      }
				    },
				    {
				       text: "Cancel",
					   click: function() {
					     $( this ).dialog( "close" );
					     return false;
					   }
				    }
				  ]
				});

			return false;
		});
	});
}