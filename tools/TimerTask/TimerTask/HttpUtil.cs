using System;
using System.Text;
using RestSharp;

namespace TimerTask {
    public class HttpUtil {

        /**
         * 发送GET请求 DF 2019年1月8日19:45:19
         */
        public static string Get(string url) {
            try {
                var client = new RestClient(Constant.ApiUrl + url);
                var request = new RestRequest(Method.GET);
                IRestResponse response = client.Execute(request);
                return response.Content;
            }
            catch (Exception e) {
                Console.WriteLine(e);
                return e.ToString();
            }

            return "";
        }

    }
}