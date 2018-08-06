package android.luna.Activity.ServiceUi.fragment;


import android.app.Fragment;
import android.luna.Activity.ServiceUi.Adapter.HelpAdapter;
import android.luna.Activity.ServiceUi.Adapter.ListViewFaqAdapter;
import android.luna.Data.module.FAQItem;
import android.luna.Utils.FileHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class HelpFragment extends Fragment implements View.OnClickListener,OnLoadCompleteListener {
    private Button btn_pdf,btn_video,btn_faq;
    private PDFView pdfView;
    private GridView gv_file;
    private HelpAdapter helpAdapter;
    private ListView lv_faq;
    private List<String> _data = new ArrayList<>();
    private List<FAQItem> _faqdata = new ArrayList<>();
    private ListViewFaqAdapter listViewFaqAdapter = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        InitView(view);
        return view;
    }

    private void initdata()
    {
        _data = FileHelper.getPDFFile(new File(FileHelper.PATH_HELP_SERVICE));
        String[] questions = getResources().getStringArray(R.array.faq_questions);
        String[] answers = getResources().getStringArray(R.array.faq_answers);
        FAQItem tmp;
        int count = questions.length>=answers.length? answers.length:questions.length;
        for (int i= 0 ;i<count;i++)
        {
            tmp = new FAQItem(questions[i],answers[i]);
            _faqdata.add(tmp);
        }
        listViewFaqAdapter = new ListViewFaqAdapter(getActivity(),_faqdata);
    }
    private void InitView(View view)
    {
        initdata();
        pdfView = view.findViewById(R.id.pdfView);
        btn_pdf = view.findViewById(R.id.btn_pdf);
        lv_faq = view.findViewById(R.id.lv_faq);
        btn_video = view.findViewById(R.id.btn_video);
        gv_file = view.findViewById(R.id.gv_file);
        btn_faq = view.findViewById(R.id.btn_faq);
        btn_pdf.setOnClickListener(this);
        btn_video.setOnClickListener(this);
        btn_faq.setOnClickListener(this);
        lv_faq.setAdapter(listViewFaqAdapter);
    }

    @Override
    public void onClick(View v) {
        int id =v.getId();
        switch (id)
        {
            case R.id.btn_faq:
                lv_faq.setVisibility(View.VISIBLE);
                pdfView.setVisibility(View.GONE);
                gv_file.setVisibility(View.GONE);

                break;
            case R.id.btn_pdf:
                lv_faq.setVisibility(View.GONE);
                pdfView.setVisibility(View.GONE);
                gv_file.setVisibility(View.VISIBLE);
                helpAdapter = new HelpAdapter(getActivity(),_data);
                helpAdapter.setOnFileSelect(new HelpAdapter.OnFileSelect() {
                    @Override
                    public void FileSelect(String path) {
                        pdfView.fromFile(new File(path))
                                .enableSwipe(true)
                                .swipeHorizontal(true)
                                .defaultPage(0)
                                .enableDoubletap(true)
                                .scrollHandle(new DefaultScrollHandle(getActivity()))
                                .enableAnnotationRendering(true)
                                .spacing(10)
                                .onLoad(HelpFragment.this)
                                .pageFitPolicy(FitPolicy.BOTH)
                                .load();
                        lv_faq.setVisibility(View.GONE);
                        gv_file.setVisibility(View.GONE);
                        pdfView.setVisibility(View.VISIBLE);
                    }
                });
                gv_file.setAdapter(helpAdapter);
                break;
        }
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
    }
}
