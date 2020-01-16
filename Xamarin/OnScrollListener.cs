using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Support.V7.Widget;
using Android.Util;
using Android.Views;
using Android.Widget;
using static GoogleBooks.Network.Requests;

namespace GoogleBooks
{
    public class OnScrollListener : RecyclerView.OnScrollListener
    {
        private IScrollStatus scrollStatus;

        private GridLayoutManager LayoutManager;

        private int previousTotal;

        private bool isLoading = true;

        public OnScrollListener(IScrollStatus scrollStatus, GridLayoutManager gridLayoutManager)
        {
            this.scrollStatus = scrollStatus;
            this.LayoutManager = gridLayoutManager;
            //LayoutManager = layoutManager;
        }

        public override void OnScrolled(RecyclerView recyclerView, int dx, int dy)
        {
            base.OnScrolled(recyclerView, dx, dy);

            int visibleItemCount = recyclerView.ChildCount;
            int totalItemCount = recyclerView.GetAdapter().ItemCount;
            int pastVisiblesItems = LayoutManager.FindFirstVisibleItemPosition();
            if (isLoading && totalItemCount > previousTotal)
            {
                isLoading = false;
                previousTotal = totalItemCount;
            }
            else if ((visibleItemCount + pastVisiblesItems) >= totalItemCount)
            {
                isLoading = true;
                if (!RestInfo.onGoingRequest)
                    scrollStatus.MaxScrollReached(totalItemCount);
            }            
        }

        public interface IScrollStatus
        {
            void MaxScrollReached(int currentItemNumber);
        }
    }
}