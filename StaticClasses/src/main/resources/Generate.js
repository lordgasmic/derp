<script type="text/javascript">

	var prng = uheprng();	// instantiate our uheprng for requesting PRNs
	var eventCount = 0;		// this counts events to introduce a (small) bit of additional entropy
	var i,s = '';				// general purpose local vars

	periodicRehash();			// startup our periodic rehasher

	// this 'Generate' function is called whenever the user presses the "Generate Random Numbers" button on the web page.
	// it takes the currently displayed contents of the "SeedKey" region to initialize the UHEPRNG into a known state,
	// then generates the user-specified number of pseudo-random numbers having the requested range (0 to n-1).
	function Generate() {
		var display = '';																	// this is the string that we'll be placing into the PRN display DIV
		var range = document.getElementById( 'RngRange' ).value;				// pull the form's parameters for our generation
		var count = document.getElementById( 'RngCount' ).value;
		var digits = Math.floor( Math.LOG10E * Math.log( range-1 ) ) + 1;	// maximum number of digits in the "range"

		// perform some preliminary parameter sanity checking
		if ( range <= 1 ) display += '<p><center>The "Range" specified must be at least "2" so that values can be 0 or 1 &#8212; thus 2 values.</center></p>';
		if ( count == 0 ) display += '<p><center>The "Count" of random values requested must be at least 1.</center></p>';
		if ( display == '' ) {
			// we are about to generate our PRNs, so we capture the current "SeedKey"
			// from the webpage's form field and use it to setup our PRNG
			prng.initState();																// init the PRNG and its private hash function
			prng.hashString( document.getElementById( 'seedkey' ).value );
			
			// with the PRNG initialized into a known starting state by the provided SeedKey
			// we now pull the requested number of pseudo-random numbers from our the generator
			for ( i = 0; i < count; i++ ) {					// iterate through, concatenating PRNs to the 'display' string
				s = prng(range).toString();					// call our PRNG and convert the return to a string
				while ( s.length < digits ) s = '0' + s;	// left-zero pad the result out to the maximum length of digits
				display += s + ' ';								// concatenate the new string onto our growing 'display' string
			}
		}
		// with all of the numbers collected, place the final 'display' string into the 'prns' DIV
		document.getElementById( 'prns' ).innerHTML = display;
		// now that we've populated the DIV, show what we have done
		document.getElementById( 'prndiv' ).style.display = 'block';
	}	

	// this 'Remove' function is called whenever the user presses either of the "Remove Random Numbers" buttons
	// accompanying the random number display.  It simply sets the random number's DIV style to "display:none"
	// and also removes the numbers from the DIV's HTML, thus freeing whatever memory might have been used.
	function Remove() {
		// remove the DIV from our page
		document.getElementById( 'prndiv' ).style.display = 'none';
		// and remove the text from the DIV after hiding it
		document.getElementById( 'prns' ).innerHTML = '';
	}

	// this 'addEntropy' function calls the UHEPRNG's built-in hashing function with whatever (optional)
	// arguments it is provided, plus a count, the current time and a random value from the local browser
	// as a means of pouring additional entropy into the UHEPRNG's internal state.
	// Note that the invocation of the UHEPRNG initializes the PRNG with a large amount of initial entropy.
	function addEntropy() {
		prng.addEntropy();
		var prngState = prng.string(256);				// obtain 256 random printable characters
		for ( var s='',i=0; i < 8; i++ ) {					// for displaying, break the 256-chars into 8 lines of 32
			if ( i ) s += String.fromCharCode(10);			// firefox needs a CR/LF at the end of each 32-character line
			s += prngState.substr(i*32,32);					// concatenate the string pieces into a larger composite
		}
		document.getElementById( 'seedkey' ).value = s;	// display the updated result in the webform
	}

	// this bit of "eye candy" is called upon page load and simply recalls itself five times per second. as
	// long as the "Randomize the Seed Key" option is enabled, it calls 'addEntropy()' to scramble the SeedKey
	function periodicRehash() {
		if ( document.getElementById( 'UpdateKey' ).checked ) addEntropy();
		setTimeout( periodicRehash, 200 );	// re-call THIS function after the indicated interval
	}	
</script>