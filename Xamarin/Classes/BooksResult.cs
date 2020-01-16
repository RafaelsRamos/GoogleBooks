using System.Collections.Generic;
using Newtonsoft.Json;

namespace GoogleBooks.Classes
{
    public class BooksResult
    {

        [JsonProperty("kind")]
        public string kind { get; set; }

        [JsonProperty("totalItems")]
        public int totalItems { get; set; }

        [JsonProperty("items")]
        public List<Book> books { get; set; }
    }

}





