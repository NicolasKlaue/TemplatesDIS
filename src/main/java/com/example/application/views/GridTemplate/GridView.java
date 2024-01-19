package com.example.application.views.GridTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Optional;

import com.example.application.MsCode;
import com.example.application.views.MainLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Grid Template")
@Route(value = "grid", layout = MainLayout.class)
public class GridView extends VerticalLayout {
     public Grid<MsCode> dataGrid = new Grid<>(MsCode.class, false);
     public Dialog AddMsCodeDialog = new Dialog();
     public Dialog editMsCodeDialog = new Dialog();
     public Optional<MsCode> optionalMsCode;
     public Button deleteButton = new Button("Delete");
     public Button editButton = new Button("Edit Mscode");

     public GridView() {
          setSizeFull();
          setupGrid();
          AddMsCodeDialog = setupDialog(false, "", "", "", "", "", "", "", "");
          deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
          deleteButton.setEnabled(false);
          deleteButton.addClickListener(event -> DeleteMsCode());
          Button OpenDialog = new Button("Add MsCode");
          Button FillGrid = new Button("Reload Grid");
          FillGrid();
          editButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
          editButton.setEnabled(false);
          editButton.addClickListener(event -> EditMsCode());
          HorizontalLayout buttonLayout = new HorizontalLayout();
          buttonLayout.add(FillGrid, OpenDialog, deleteButton, editButton);

          FillGrid.addClickListener(event -> FillGrid());
          OpenDialog.addClickListener(event -> AddMsCodeDialog.open());
          add(buttonLayout, dataGrid);
     }

     private void EditMsCode() {
          editMsCodeDialog = setupDialog(
                    true,
                    optionalMsCode.get().getMsCode(),
                    optionalMsCode.get().getEstCode(),
                    optionalMsCode.get().getFlag(),
                    optionalMsCode.get().getYear(),
                    String.valueOf(optionalMsCode.get().getSe()),
                    String.valueOf(optionalMsCode.get().getEstimate()),
                    String.valueOf(optionalMsCode.get().getLowerCIB()),
                    String.valueOf(optionalMsCode.get().getUpperCIB()));
          editMsCodeDialog.open();

     }

     private void DeleteMsCode() {
          System.out.println("The MsCode: " + optionalMsCode.get().getId() + " Would be deleted right about now");
          Notification.show("MsCode with ID: " + optionalMsCode.get().getId() + " has been deleted");
          DeleteController(optionalMsCode.get().getId());
          dataGrid.deselectAll();
          deleteButton.setEnabled(false);
     }

     private void DeleteController(String id) {
          HttpClient httpClient = HttpClient.newBuilder().build();

          HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/mscode/" + id))
                    .DELETE()
                    .build();

          try {
               HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

               if (response.statusCode() == 200) {
                    dataGrid.getDataProvider().refreshAll();
                    FillGrid();

               } else {
                    Notification.show("Error: API returned status code " + response.statusCode());
               }
          } catch (IOException | InterruptedException ex) {
               Notification.show("Error: " + ex.getMessage());
          }
     }

     private Dialog setupDialog(Boolean Edit, String msCode, String estCode, String flag, String year, String se,
               String estimate, String lowerCIB, String upperCIB) {
          Dialog Temp = new Dialog();
          VerticalLayout dialogContent = new VerticalLayout();
          HorizontalLayout dialogFields = new HorizontalLayout();
          dialogFields.setPadding(false);
          dialogFields.setSpacing(false);

          VerticalLayout firstColumn = new VerticalLayout();
          TextField msCodeField = new TextField("MsCode");
          msCodeField.setValue(msCode);
          firstColumn.add(msCodeField);
          TextField estCodeField = new TextField("EstCode");
          estCodeField.setValue(estCode);
          firstColumn.add(estCodeField);
          TextField seField = new TextField("Se");
          seField.setValue(se);
          firstColumn.add(seField);
          TextField flagField = new TextField("Flag");
          flagField.setValue(flag);
          firstColumn.add(flagField);

          VerticalLayout secondColumn = new VerticalLayout();
          TextField yearField = new TextField("Year");
          yearField.setValue(year);
          secondColumn.add(yearField);
          TextField estimateField = new TextField("Estimate");
          estimateField.setValue(estimate);
          secondColumn.add(estimateField);
          TextField lowerCIBField = new TextField("Lower CIB");
          lowerCIBField.setValue(lowerCIB);
          secondColumn.add(lowerCIBField);
          TextField upperCIBField = new TextField("Upper CIB");
          upperCIBField.setValue(upperCIB);
          secondColumn.add(upperCIBField);

          Button saveButton = new Button("Save");
          Button cancelButton = new Button("Cancel");

          HorizontalLayout buttonLayout = new HorizontalLayout();
          buttonLayout.add(saveButton, cancelButton);
          dialogFields.add(firstColumn, secondColumn);
          dialogContent.add(dialogFields, buttonLayout);
          Temp.add(dialogContent);

          saveButton.addClickListener(event -> {
               MsCode newMsCode = new MsCode(
                         msCodeField.isEmpty() ? "" : msCodeField.getValue(),
                         yearField.isEmpty() ? "" : yearField.getValue(),
                         seField.isEmpty() ? 0.0f : Float.parseFloat(seField.getValue()),
                         estCodeField.isEmpty() ? "" : estCodeField.getValue(),
                         estimateField.isEmpty() ? 0.0f : Float.parseFloat(estimateField.getValue()),
                         lowerCIBField.isEmpty() ? 0.0f : Float.parseFloat(lowerCIBField.getValue()),
                         upperCIBField.isEmpty() ? 0.0f : Float.parseFloat(upperCIBField.getValue()),
                         flagField.isEmpty() ? "" : flagField.getValue());
               if (Edit) {
                    newMsCode.setId(optionalMsCode.get().getId());
                    PutController(newMsCode);
               } else {
                    AddController(newMsCode);
               }
               Temp.close();
          });

          cancelButton.addClickListener(event -> {
               Temp.close();
          });
          return Temp;
     }

     private void AddController(MsCode newMsCode) {
          HttpClient httpClient = HttpClient.newBuilder().build();
          Gson gson = new Gson();
          String body = gson.toJson(newMsCode);

          HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/mscode"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

          try {
               HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

               if (response.statusCode() == 200) {
                    dataGrid.getDataProvider().refreshAll();
                    FillGrid();
                    Notification.show("Success");

               } else {
                    Notification.show("Error: API returned status code " + response.statusCode());
               }
          } catch (IOException | InterruptedException ex) {
               Notification.show("Error: " + ex.getMessage());
          }
     }

     private void PutController(MsCode newMsCode) {
          HttpClient httpClient = HttpClient.newBuilder().build();
          Gson gson = new Gson();
          String body = gson.toJson(newMsCode);
          System.out.println(body);

          HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/mscode"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .build();

          try {
               HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

               if (response.statusCode() == 200) {
                    FillGrid();
                    dataGrid.getDataProvider().refreshAll();
                    Notification.show("Success");

               } else {
                    Notification.show("Error: API returned status code " + response.statusCode());
               }
          } catch (IOException | InterruptedException ex) {
               Notification.show("Error: " + ex.getMessage());
          }
     }

     private void setupGrid() {
          dataGrid.addColumn(MsCode::getId).setHeader("ID");
          dataGrid.addColumn(MsCode::getMsCode).setHeader("MsCode").setSortable(true);
          dataGrid.addColumn(MsCode::getEstCode).setHeader("EstCode");
          dataGrid.addColumn(MsCode::getFlag).setHeader("Flag");
          dataGrid.addColumn(MsCode::getYear).setHeader("Year");
          dataGrid.addColumn(MsCode::getSe).setHeader("Se");
          dataGrid.addColumn(MsCode::getEstimate).setHeader("Estimate");
          dataGrid.addColumn(MsCode::getLowerCIB).setHeader("Lower CIB");
          dataGrid.addColumn(MsCode::getUpperCIB).setHeader("Upper CIB");
          dataGrid.addSelectionListener(selection -> {
               optionalMsCode = selection.getFirstSelectedItem();
               if (optionalMsCode.isPresent()) {
                    System.out.println("The MsCode picked is: " + optionalMsCode.get().getId());
                    deleteButton.setEnabled(true);
                    editButton.setEnabled(true);
               } else {
                    deleteButton.setEnabled(false);
                    editButton.setEnabled(false);

               }
          });

     }

     public void FillGrid() {
          HttpClient httpClient = HttpClient.newBuilder().build();

          HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/mscode"))
                    .GET()
                    .build();

          try {
               HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

               if (response.statusCode() == 200) {
                    String responseBody = response.body();
                    Gson gson = new Gson();
                    List<MsCode> msCodes = gson.fromJson(responseBody, new TypeToken<List<MsCode>>() {
                    }.getType());
                    dataGrid.setItems(msCodes);
                    dataGrid.getDataProvider().refreshAll();

                    Notification.show("Received " + msCodes.size() + " MsCodes");
               } else {
                    Notification.show("Error: API returned status code " + response.statusCode());
               }
          } catch (IOException | InterruptedException ex) {
               Notification.show("Error: " + ex.getMessage());
          }
     }
}
