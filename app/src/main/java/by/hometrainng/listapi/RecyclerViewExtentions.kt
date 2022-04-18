package by.hometrainng.listapi

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView



fun RecyclerView.addSpaceDecoration(bottomSpace: Int) {  // TODO сделать через @DimenRes
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val itemCount = parent.adapter?.itemCount ?: return // достаем количесвто элементов
            val position = parent.getChildAdapterPosition(view) //по viewHolder находит позицию элемента для котрого все это делается
            if (position != itemCount - 1) { // что бы отступ не был на последнем элементе
                outRect.bottom = bottomSpace   // отступ между элементами в пикселях, но можно вынимать из ресурсов
            }
        }
    })
}