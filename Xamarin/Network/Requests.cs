using GoogleBooks.Classes;
using Newtonsoft.Json;
using System.Net.Http;

namespace GoogleBooks.Network
{
    public class Requests
    {
        private readonly HttpClient httpClient = new HttpClient();

        public async System.Threading.Tasks.Task<BooksResult> GetBooks()
        {
            string content = await httpClient.GetStringAsync(RestInfo.BaseUrl + "/volumes?key=" + RestInfo.ApiKey + "&q=Android" + "&maxResults=" + RestInfo.requestSize);
            return JsonConvert.DeserializeObject<BooksResult>(content);
        }

        public async System.Threading.Tasks.Task<BooksResult> GetBooks(string searchQuery)
        {
            string content = await httpClient.GetStringAsync(RestInfo.BaseUrl + "/volumes?key=" + RestInfo.ApiKey + "&q=" + searchQuery + "&maxResults=" + RestInfo.requestSize);
            return JsonConvert.DeserializeObject<BooksResult>(content);
        }

        public async System.Threading.Tasks.Task<BooksResult> GetBooks(string searchQuery, int startIndex)
        {
            string content = await httpClient.GetStringAsync(RestInfo.BaseUrl + "/volumes?key=" + RestInfo.ApiKey + "&q=" + searchQuery + "&startIndex=" + startIndex.ToString() + "&maxResults=" + RestInfo.requestSize);
            return JsonConvert.DeserializeObject<BooksResult>(content);
        }

        public class RestInfo
        {
            public static string BaseUrl => "https://www.googleapis.com/books/v1";
            public static string ApiKey => "AIzaSyDMs6MkR1c_jUZuIg_C_ZE7enqXVro2AnY";

            public static int requestSize => 20;

            public static bool onGoingRequest { get; set; }
        }

    }


}