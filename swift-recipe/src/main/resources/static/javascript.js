/**
 * SWIFTRECIPE PAGINATION FUNCTION
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the pagination function for the dashboard.
 */

document.addEventListener("DOMContentLoaded", function() {

    // Initializing constants and variables
    const gridContainer = document.getElementById("row");
    const paginationContainer = document.getElementById("pagination");
    const itemsPerPage = 10;
    const totalItems = document.querySelectorAll(".card").length;
    const totalPages = Math.ceil(totalItems / itemsPerPage);

    /**
     * Shows the specified page and hides the others
     * 
     * @param {number} pageNumber - The page number ot show
     */
    function showPage(pageNumber) {
        const start = (pageNumber - 1) * itemsPerPage;
        const end = start + itemsPerPage;

        // Show or hide items based on the page number
        document.querySelectorAll(".card").forEach((item, index) => {
            item.style.display = index >= start && index < end ? "block" : "none";
        });

        // Highlights the selected pagination number
        document.querySelectorAll(".pagination button").forEach((button) => {
            button.classList.remove("selected");
        });
    
        document.getElementById(`page-${pageNumber}`).classList.add("selected");
    }

    /**
     * Create pagination buttons based on the total number of pages
     */
    function createPaginationButtons() {
        for (let i = 1; i <= totalPages; i++) {
            const button = document.createElement("button");
            button.innerText = i;
            button.id = `page-${i}`;
            button.addEventListener("click", function() {
                showPage(i);
            });
            paginationContainer.appendChild(button);
        }
    }

    // Initialize pagination buttons and show the first page
    createPaginationButtons();
    showPage(1);
});

/**
 * Performs a search based on the input text
 */
function search() {
    var searchText = document.getElementById("searchInput").value;
    window.location.href = "/results?query=" + encodeURIComponent(searchText);
}

// Enable or disable search button based on the input text
document.getElementById("searchInput").addEventListener("input", function() {
    var searchButton = document.getElementById("searchButton");
    if (this.value.trim() !== "") {
        searchButton.disabled = false;
    } else {
        searchButton.disabled = true;
    }
});