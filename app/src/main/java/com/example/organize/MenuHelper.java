package com.example.organize;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class MenuHelper {

    public static void setupMenu(Context context, View anchorView) {
        PopupMenu popupMenu = new PopupMenu(context, anchorView);
        popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            Class<?> targetActivity = null;

            int itemId = item.getItemId();
            if (itemId == R.id.nav_tarefas) {
                targetActivity = ListaTarefasActivity.class;
            } else if (itemId == R.id.nav_lembretes) {
                targetActivity = ListaLembretesActivity.class;
            } else if (itemId == R.id.nav_habitos) {
                targetActivity = ListaHabitosActivity.class;
            }

            if (targetActivity != null) {
                context.startActivity(new Intent(context, targetActivity));
                return true;
            }
            return false;
        });

        popupMenu.show();
    }
}
