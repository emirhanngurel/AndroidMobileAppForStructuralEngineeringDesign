package com.gema.stairreinforcement;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.ListFormatter;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.gema.stairreinforcement.databinding.FragmentReportsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ReportsFragment extends Fragment {

    private FragmentReportsBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email;

    SendEmailService mail = new SendEmailService(getContext());

    private RecyclerView recyclerView;

    CalculationAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReportsBinding.inflate(inflater,container,false);
        View view = binding.getRoot();


        if(user!=null) {
            for (UserInfo profile : user.getProviderData()) {
                email = profile.getEmail();
            }
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new CalculationAdapter.WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        CollectionReference ref = FirebaseFirestore.getInstance().collection("/calculation");
        Query query = ref.orderBy("date", Query.Direction.DESCENDING)
                .whereEqualTo("email",email);
        FirestoreRecyclerOptions<Calculation> options = new FirestoreRecyclerOptions.Builder<Calculation>()
                .setQuery(query, Calculation.class)
                .build();
        adapter = new CalculationAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CalculationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) throws FileNotFoundException {
                generatePDF(documentSnapshot);


            }
        });

        return view;
    }

    public void generatePDF(DocumentSnapshot documentSnapshot) throws FileNotFoundException {
        Date date = documentSnapshot.getDate("date");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm", Locale.US);
        String pdfPath = getActivity().getFilesDir().getAbsolutePath();
        File file = new File(pdfPath, "Report-" + formatter.format(date) + ".pdf");
        String mailPath = pdfPath + "/Report-" + formatter.format(date) + ".pdf";
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);


        pdfDocument.setDefaultPageSize(PageSize.A4);
        document.setMargins(20,20,20,20);

        Drawable d = getActivity().getDrawable(R.drawable.ekran_resmi_2023_04_24_17_28_25);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bitmapData = stream.toByteArray();

        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);

        Paragraph calcTitle = new Paragraph("Stair Calculation Result Report")
                .setBold()
                .setFontSize(24)
                .setTextAlignment(TextAlignment.CENTER);

        Paragraph calcDesc = new Paragraph("This report has been automatically created and sent to you using the data you have saved in the database on the date you have selected.")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18);

        Paragraph inputs = new Paragraph("Inputs")
                .setBold()
                .setTextAlignment(TextAlignment.CENTER).setFontSize(20);

        float[] width = {200f,200f};

        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(new Cell().add(new Paragraph("Date")));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(documentSnapshot.getDate("date")))));

        table.addCell(new Cell().add(new Paragraph("Number of steps")));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(documentSnapshot.getLong("numOfSteps")))));

        table.addCell(new Cell().add(new Paragraph("Rise of step")));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(documentSnapshot.getLong("rise")))));

        table.addCell(new Cell().add(new Paragraph("Run of step")));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(documentSnapshot.getLong("runOfStep")))));

        table.addCell(new Cell().add(new Paragraph("Width of stair")));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(documentSnapshot.getLong("widthOfStair")))));

        table.addCell(new Cell().add(new Paragraph("Thickness")));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(documentSnapshot.getLong("thickness")))));

        Paragraph outputs = new Paragraph("Outputs")
                .setBold()
                .setTextAlignment(TextAlignment.CENTER).setFontSize(20);

        Table table2 = new Table((width));
        table2.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table2.addCell(new Cell().add(new Paragraph("Total Run")));
        table2.addCell(new Cell().add(new Paragraph(String.valueOf(documentSnapshot.getDouble("tru")))));

        table2.addCell(new Cell().add(new Paragraph("Total Rise")));
        table2.addCell(new Cell().add(new Paragraph(String.valueOf(documentSnapshot.getDouble("tri")))));

        table2.addCell(new Cell().add(new Paragraph("Stair Slope")));
        table2.addCell(new Cell().add(new Paragraph(String.valueOf(documentSnapshot.getDouble("ss")))));

        table2.addCell(new Cell().add(new Paragraph("Cubic Feet")));
        table2.addCell(new Cell().add(new Paragraph(String.valueOf(documentSnapshot.getDouble("feet")))));

        table2.addCell(new Cell().add(new Paragraph("Cubic Yards")));
        table2.addCell(new Cell().add(new Paragraph(String.valueOf(documentSnapshot.getDouble("yard")))));

        table2.addCell(new Cell().add(new Paragraph("Cubic Meter")));
        table2.addCell(new Cell().add(new Paragraph(String.valueOf(documentSnapshot.getDouble("meter")))));

        Paragraph cost = new Paragraph("Estimated costs according to exchange rates")
                .setBold()
                .setTextAlignment(TextAlignment.CENTER).setFontSize(20);

        Table table3 = new Table(width);
        table3.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table3.addCell(new Cell().add(new Paragraph("Turkish Lira (TRY)")));
        table3.addCell(new Cell().add(new Paragraph(String.valueOf(documentSnapshot.getDouble("lira")))));

        table3.addCell(new Cell().add(new Paragraph("US Dollar (USD)")));
        table3.addCell(new Cell().add(new Paragraph(String.valueOf(documentSnapshot.getDouble("dollar")))));

        table3.addCell(new Cell().add(new Paragraph("Euro (EUR)")));
        table3.addCell(new Cell().add(new Paragraph(String.valueOf(documentSnapshot.getDouble("euro")))));

        Paragraph warn = new Paragraph("Attention! The exchange rates used in the estimated calculations are calculated according to the exchange rates of the day you recorded the calculation.")
                .setTextAlignment(TextAlignment.CENTER).setFontSize(12);


        document.add(image);
        document.add(calcTitle);
        document.add(calcDesc);
        document.add(inputs);
        document.add(table);
        document.add(outputs);
        document.add(table2);
        document.add(cost);
        document.add(table3);
        document.add(warn);
        document.close();

        SendEmailService.getInstance(getContext().getApplicationContext()).emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                SendEmailService.getInstance(getContext().getApplicationContext()).SendEmail(email,date,mailPath);
            }
        });

        Toast.makeText(getActivity(),"PDF Created",Toast.LENGTH_SHORT).show();

        if (file.exists()) //Checking if the file exists or not
        {
            Uri path = FileProvider.getUriForFile(getContext(),getActivity().getPackageName() + ".provider",file);
            Intent objIntent = new Intent(Intent.ACTION_VIEW);
            objIntent.setDataAndType(path, "application/pdf");
            objIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //objIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(objIntent);//Starting the pdf viewer
        } else {

            Toast.makeText(getActivity(), "The file not exists! ", Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}