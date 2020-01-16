using GoogleBooks.Classes;
using GoogleBooks.Network;
using System.ComponentModel;
using Xamarin.Forms.Extended;

using System.Collections.Generic;
using System.Threading.Tasks;
using System.Runtime.CompilerServices;

namespace GoogleBooks.ViewModels
{
    public class MainViewModel : INotifyPropertyChanged
    {

        private bool _isBusy;
        private int startIndex = 1;

        public string query;

        public InfiniteScrollCollection<string> Items { get; }
        
        public bool IsBusy
        {
            get => _isBusy;
            set
            {
                _isBusy = value;
                OnPropertyChanged();
            }
        }
        
        public MainViewModel()
        {
            Items = new InfiniteScrollCollection<string>
            {
                OnLoadMore = async () =>
                {
                    IsBusy = true;

                    // load the next page
                    //var page = Items.Count / PageSize;

                    BooksResult result = await LoadBooksPagination(query, startIndex);
                    var items = resolvingResult(result);

                    IsBusy = false;

                    // return the items that need to be added
                    return items;
                },
                OnCanLoadMore = () =>
                {
                    return Items.Count < 44;
                }
            };

            LoadBooks();
        }

        public event PropertyChangedEventHandler PropertyChanged;

        protected virtual void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        private async void LoadBooks()
        {
            //string content = await httpClient.GetStringAsync(BASE_URL + "/volumes?key=" + RestInfo.ApiKey + "&q=Android");
            //Log.Debug("MAIN STUFF", content);
            //BooksResult books = JsonConvert.DeserializeObject<BooksResult>(content);

            Requests requests = new Requests();
            BooksResult result = await requests.GetBooks();

            resolvingResult(result);
        }

        public async void LoadBooksByName(string seachQuery)
        {

            Requests requests = new Requests();
            BooksResult result = await requests.GetBooks(seachQuery);

            resolvingResult(result);
        }

        private async Task<BooksResult> LoadBooksPagination(string seachQuery, int startIndex)
        {

            Requests requests = new Requests();
            return await requests.GetBooks(seachQuery, startIndex);
        }

        // Unpacking the result into an array of books.
        private List<string> resolvingResult(BooksResult result)
        {
            Book[] books = result.books.ToArray();
            List<string> newItems = new List<string>();
            foreach (Book book in books)
            {
                newItems.Add(book.id);
            }
            return newItems;
        }
    }
}

