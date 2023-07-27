function studentResultGrid() {

	let dataView;
	let grid;
	let data1 = document.getElementById("result").value;
	let data = JSON.parse(data1);
	console.log(data);

	/* set unique it to array */

	let nextId = 1;
	data.forEach(function(item) {
		// Generate a unique id using the nextId value
		let itemId = "item_" + nextId;

		// Set the id property of the item object
		item.id = itemId;

		// Increment the nextId value
		nextId++;
	});

	// Use the updated items array list with unique ids
	console.log(data);
	//console.log(dataList);
	/* unique id end */
	
	/*date formator start */
	function dateFormatter(row, cell, value, columnDef, dataContext) {
		const date = new Date(value);
		let getDay = date.toLocaleString("default", { day: "2-digit" });
		let getMonth = date.toLocaleString("default", { month: "2-digit" });
		let getYear = date.toLocaleString("default", { year: "numeric" }); // Set the desired date format
		const formattedDate = getDay + "-" + getMonth + "-" + getYear;// Format the date as a string
		return formattedDate; // Return the formatted date string
	}
	/*date formator end */
	
	/* check box funtion start */
	function checkboxFormatter(row, cell, value, columnDef, dataContext) {
		let a = dataContext.donationCode;
		return '<input type="checkbox" value="' + a + '" name="checkName" id="checkBox"' + (value ? 'checked="checked"' : '') + '/>';
	}

	//let check= document.getElementById("checkBox");
	let check = document.querySelectorAll('input[type="checkbox"]:checked');
	console.log(check);

	/* check box funtion end */
	
	function buttonFormatter(row, cell, value, columnDef, dataContext) {
	let a = dataContext.userId ;
    return '<form action="/resultPopup" metod="get"><button type="submit"  class="buttons" name="userId"  value="'+a+'">Add Marks</button></form>';
    }
	
	let columns = [ {
		id: "examId",
		name: "Exam Id",
		field: "examId",
		type: 'numberColumn',
		width: 100,
		sortable: true
	}, {
		id: "subjectId",
		name: "Subject Id",
		field: "subjectId",
		sortable: true
	}, {
		id: "subjectName",
		name: "Subject Name",
		field: "subjectName",
		sortable: true
	},{
		id: "semesterId",
		name: "Semester Id",
		field: "semesterId",
		sortable: true
	},{
		id : "marks",
		name : "Marks",
		field : "marks",
		width:200
	}];

	let options = {
		editable: true,
		enableAddRow: false,
		enableCellNavigation: true,
		asyncEditorLoading: true,
		forceFitColumns: false,
		showHeaderRow: true,
		headerRowHeight: 30,
		explicitInitialization: true,
		topPanelHeight: 25
	};

	let columnFilters = {};

	let sortcol = "title";
	let sortdir = 1;
	let percentCompleteThreshold = 0;
	let searchString = "";

	function requiredFieldValidator(value) {
		if (value === null || value === undefined || !value.length) {
			return { valid: false, msg: "This is a required field" };
		}
		else {
			return { valid: true, msg: null };
		}
	}

	function myFilter(item, args) {
		if (item["percentComplete"] < args.percentCompleteThreshold) {
			return false;
		}

		if (args.searchString !== "" && item["title"].indexOf(args.searchString) === -1) {
			return false;
		}

		return true;
	}

	function percentCompleteSort(a, b) {
		return a["percentComplete"] - b["percentComplete"];
	}

	function comparer(a, b) {
		let x = a[sortcol], y = b[sortcol];
		return (x === y ? 0 : (x > y ? 1 : -1));
	}

	function toggleFilterRow() {
		grid.setTopPanelVisibility(!grid.getOptions().showTopPanel);
	}


	$(".grid-header .ui-icon")
		.addClass("ui-state-default ui-corner-all")
		.mouseover(function(e) {
			$(e.target).addClass("ui-state-hover")
		})
		.mouseout(function(e) {
			$(e.target).removeClass("ui-state-hover")
		});

	$(function() {
		/* filter start */
		function filter(item) {
			for (let columnId in columnFilters) {
				if (columnId !== undefined && columnFilters[columnId] !== "") {
					let column = grid.getColumns()[grid.getColumnIndex(columnId)];

					if (item[column.field] !== undefined) {
						let filterResult = typeof item[column.field].indexOf === 'function'
							? (item[column.field].indexOf(columnFilters[columnId]) === -1)
							: (item[column.field] !== columnFilters[columnId]);

						if (filterResult) {
							return false;
						}
					}
				}
			}
			return true;
		}
		/* filter end */


		//		dataView = new Slick.Data.DataView({ inlineFilters: true });
		dataView = new Slick.Data.DataView();
		grid = new Slick.Grid("#studentResultGrid", dataView, columns, options);
		grid.setSelectionModel(new Slick.RowSelectionModel());

	//	let pager = new Slick.Controls.Pager(dataView, grid, $("#pager"));
	//	let columnpicker = new Slick.Controls.ColumnPicker(columns, grid, options);

		// header row start
		dataView.onRowCountChanged.subscribe(function(e, args) {
			grid.updateRowCount();
			grid.render();
		});

		dataView.onRowsChanged.subscribe(function(e, args) {
			grid.invalidateRows(args.rows);
			grid.render();
		});

		$(grid.getHeaderRow()).delegate(":input", "change keyup",
			function(e) {
				let columnId = $(this).data("columnId");
				if (columnId != null) {
					columnFilters[columnId] = $.trim($(this).val());
					dataView.refresh();
				}
			});

		grid.onHeaderRowCellRendered.subscribe(function(e, args) {
			$(args.node).empty();
			$("<input type='text'>").data("columnId", args.column.id).val(
				columnFilters[args.column.id]).appendTo(args.node);

		});
		// header row end


		// move the filter panel defined in a hidden div into grid top panel
		$("#inlineFilterPanel")
			.appendTo(grid.getTopPanel())
			.show();

		grid.onCellChange.subscribe(function(e, args) {
			dataView.updateItem(args.item.id, args.item);
		});
/*
		grid.onAddNewRow.subscribe(function(e, args) {
			let item = { "num": data.length, "id": "new_" + (Math.round(Math.random() * 10000)), "title": "New task", "duration": "1 day", "percentComplete": 0, "start": "01/01/2009", "finish": "01/01/2009", "effortDriven": false };
			$.extend(item, args.item);
			dataView.addItem(item);
		});
*/
		grid.onKeyDown.subscribe(function(e) {
			// select all rows on ctrl-a
			if (e.which !== 65 || !e.ctrlKey) {
				return false;
			}

			let rows = [];
			for (let i = 0; i < dataView.getLength(); i++) {
				rows.push(i);
			}

			grid.setSelectedRows(rows);
			e.preventDefault();
		});

		grid.onSort.subscribe(function(e, args) {
			sortdir = args.sortAsc ? 1 : -1;
			sortcol = args.sortCol.field;

			if ($.browser.msie && $.browser.version <= 8) {
				// using temporary Object.prototype.toString override
				// more limited and does lexicographic sort only by default, but can be much faster

				let percentCompleteValueFn = function() {
					let val = this["percentComplete"];
					if (val < 10) {
						return "00" + val;
					} else if (val < 100) {
						return "0" + val;
					} else {
						return val;
					}
				};

				// use numeric sort of % and lexicographic for everything else
				dataView.fastSort((sortcol === "percentComplete") ? percentCompleteValueFn : sortcol, args.sortAsc);
			} else {
				// using native sort with comparer
				// preferred method but can be very slow in IE with huge datasets
				dataView.sort(comparer, args.sortAsc);
			}
		});

		// wire up model events to drive the grid
		dataView.onRowCountChanged.subscribe(function(e, args) {
			grid.updateRowCount();
			grid.render();
		});

		dataView.onRowsChanged.subscribe(function(e, args) {
			grid.invalidateRows(args.rows);
			grid.render();
		});

		dataView.onPagingInfoChanged.subscribe(function(e, pagingInfo) {
			let isLastPage = pagingInfo.pageNum === pagingInfo.totalPages - 1;
			let enableAddRow = isLastPage || pagingInfo.pageSize === 0;
			let options = grid.getOptions();

			if (options.enableAddRow !== enableAddRow) {
				grid.setOptions({ enableAddRow: enableAddRow });
			}
		});


		let h_runfilters = null;
		/*
				// wire up the slider to apply the filter to the model
				$("#pcSlider,#pcSlider2").slider({
					"range": "min",
					"slide": function(event, ui) {
						Slick.GlobalEditorLock.cancelCurrentEdit();
		
						if (percentCompleteThreshold != ui.value) {
							window.clearTimeout(h_runfilters);
							h_runfilters = window.setTimeout(updateFilter, 10);
							percentCompleteThreshold = ui.value;
						}
					}
				});
		
		
				// wire up the search textbox to apply the filter to the model
				$("#txtSearch,#txtSearch2").keyup(function(e) {
					Slick.GlobalEditorLock.cancelCurrentEdit();
		
					// clear on Esc
					if (e.which == 27) {
						this.value = "";
					}
		
					searchString = this.value;
					updateFilter();
				});
		*/
		function updateFilter() {
			dataView.setFilterArgs({
				percentCompleteThreshold: percentCompleteThreshold,
				searchString: searchString
			});
			dataView.refresh();
		}

		$("#btnSelectRows").click(function() {
			if (!Slick.GlobalEditorLock.commitCurrentEdit()) {
				return;
			}

			let rows = [];
			for (let i = 0; i < 10 && i < dataView.getLength(); i++) {
				rows.push(i);
			}

			grid.setSelectedRows(rows);
		});


		// initialize the model after all the events have been hooked up
		grid.init();
		dataView.beginUpdate();
		dataView.setItems(data);
		/*
		dataView.setFilterArgs({
			percentCompleteThreshold: percentCompleteThreshold,
			searchString: searchString
		});
		*/
		dataView.setFilter(filter);
		dataView.endUpdate();

		// if you don't want the items that are not visible (due to being filtered out
		// or being on a different page) to stay selected, pass 'false' to the second arg
		dataView.syncGridSelection(grid, true);

		//$("#gridContainer").resizable();
	})

}