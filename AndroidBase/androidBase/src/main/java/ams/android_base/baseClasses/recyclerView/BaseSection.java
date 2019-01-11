package ams.android_base.baseClasses.recyclerView;

//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
//import com.androidchallenge.instabug.instabugandroidchallenge.models.entites.roomDatabaseEntites.DaySection;
//import com.androidchallenge.instabug.instabugandroidchallenge.models.entites.roomDatabaseEntites.MatchEntity;
//import com.androidchallenge.instabug.instabugandroidchallenge.scenes.mainScreenScene.matchesListing.allMatchesFragment.SectionInteractionListener;
//
//import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
//import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

//public class BaseSection<Section extends DaySection, L extends SectionInteractionListener, HVH extends BaseRecyclerViewHolder, VH extends BaseRecyclerViewHolder> extends StatelessSection {
//
//    private Section section;
//    private L listener;
//
//    protected Section getSection() { return section; }
//
//    protected L getListener() { return listener; }
//
//    /**
//     * Create a stateless Section object based on {@link SectionParameters}.
//     *
//     * @param sectionParameters section parameters
//     */
//    public BaseSection(SectionParameters sectionParameters) { super(sectionParameters); }
//
//    protected void init (Section section, L listener) { this.section = section; this.listener = listener; }
//
//    @Override
//    public int getContentItemsTotal() { return 0; }
//
//    @Override
//    public RecyclerView.ViewHolder getItemViewHolder(View view) { return null; }
//
//    @Override
//    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
//        MatchEntity match = this.section.getMatchesList().get(position);
//        ((VH)holder).onBind(match, listener);
//    }
//
//    @Override
//    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
//        super.onBindHeaderViewHolder(holder);
//        ((HVH)holder).onBind(section, listener);
//    }
//}
