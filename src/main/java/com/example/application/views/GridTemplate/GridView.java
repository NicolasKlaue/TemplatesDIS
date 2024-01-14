package com.example.application.views.GridTemplate;

import java.util.ArrayList;

import com.example.application.MsCode;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Grid Template")
@Route(value = "grid", layout = MainLayout.class)
public class GridView extends VerticalLayout {
     public Grid<MsCode> dataGrid = new Grid<>(MsCode.class, false);

     public GridView() {
          setSizeFull();
          FillGrid();
          add(dataGrid);
     }

     public void FillGrid() {
          dataGrid.addColumn(MsCode::getId).setHeader("id");
          dataGrid.addColumn(MsCode::getYear).setHeader("Year");
          dataGrid.addColumn(MsCode::getEstCode).setHeader("EstCode");
          dataGrid.addColumn(MsCode::getFlag).setHeader("Flag");

          ArrayList<MsCode> data = MsCode.getData();
          this.dataGrid.setItems(data);
     }

}
