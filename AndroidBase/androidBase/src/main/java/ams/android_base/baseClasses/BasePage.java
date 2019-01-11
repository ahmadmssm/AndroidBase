package ams.android_base.baseClasses;

@SuppressWarnings("WeakerAccess")
public class BasePage<T> {

    private int itemsCount;
    private int itemsPerPage = 20;
    private int pageNumber = 1;

    public void setItemsCount(int itemsCount) { this.itemsCount = itemsCount; }

    public int getItemsPerPage() { return itemsPerPage; }

    public int getPageNumber() { return pageNumber; }


    // Helper methods
    public void resetPageCount () { this.pageNumber = 1; }

    public void incrementPageNumber () { this.pageNumber ++; }

    public int getTotalNumberOfPages() {
        float tempItemsCount = itemsCount;
        float tempItemsPerPage = itemsPerPage;
        return (int) (Math.ceil(tempItemsCount/tempItemsPerPage));
    }

    public boolean hasNext() { return pageNumber <= getTotalNumberOfPages(); }

    public void resetPage () { resetPageCount(); }

}