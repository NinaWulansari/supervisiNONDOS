package com.wahanaartha.supervisionline;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import com.wahanaartha.supervisionline.Model.ExpandableGroup;
import com.wahanaartha.supervisionline.Model.ExpandableListPosition;
import com.wahanaartha.supervisionline.viewholders.ChildViewHolder;
import com.wahanaartha.supervisionline.viewholders.GroupViewHolder;


import java.util.List;

public abstract class MultiTypeExpandableRecyclerViewAdapter<GVH extends GroupViewHolder, CVH extends ChildViewHolder>
    extends ExpandableRecyclerViewAdapter<GVH, CVH> {

  public MultiTypeExpandableRecyclerViewAdapter(List<? extends ExpandableGroup> groups) {
    super(groups);
  }

  /**
   * Implementation of RecyclerView.Adapter.onCreateViewHolder(ViewGroup, int)
   * that determines if the list item is a group or a child and calls through
   * to the appropriate implementation of either {@link #onCreateGroupViewHolder(ViewGroup, int)}
   * or {@link #onCreateChildViewHolder(ViewGroup, int)}}.
   *
   * @param parent The {@link ViewGroup} into which the new {@link android.view.View}
   * will be added after it is bound to an adapter position.
   * @param viewType The view type of the new {@code android.view.View}.
   * @return Either a new {@link GroupViewHolder} or a new {@link ChildViewHolder}
   * that holds a {@code android.view.View} of the given view type.
   */
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (isGroup(viewType)) {
      GVH gvh = onCreateGroupViewHolder(parent, viewType);
      gvh.setOnGroupClickListener(this);
      return gvh;
    } else if (isChild(viewType)) {
      CVH cvh = onCreateChildViewHolder(parent, viewType);
      return cvh;
    }
    throw new IllegalArgumentException("viewType is not valid");
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    ExpandableListPosition listPos = expandableList.getUnflattenedPosition(position);
    ExpandableGroup group = expandableList.getExpandableGroup(listPos);
    if (isGroup(getItemViewType(position))) {
      onBindGroupViewHolder((GVH) holder, position, group);

      if (isGroupExpanded(group)) {
        ((GVH) holder).expand();
      } else {
        ((GVH) holder).collapse();
      }
    } else if (isChild(getItemViewType(position))) {
      onBindChildViewHolder((CVH) holder, position, group, listPos.childPos);
    }
  }

  /**
   * Gets the view type of the item at the given position.
   *
   * @param position The flat position in the list to get the view type of
   * @return if the flat position corresponds to a child item, this will return the value returned
   * by {@code getChildViewType}. if the flat position refers to a group item this will return the
   * value returned by {@code getGroupViewType}
   */
  @Override
  public int getItemViewType(int position) {
    ExpandableListPosition listPosition = expandableList.getUnflattenedPosition(position);
    ExpandableGroup group = expandableList.getExpandableGroup(listPosition);

    int viewType = listPosition.type;
    switch (viewType) {
      case ExpandableListPosition.GROUP:
        return getGroupViewType(position, group);
      case ExpandableListPosition.CHILD:
        return getChildViewType(position, group, listPosition.childPos);
      default:
        return viewType;
    }
  }

  public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
    return super.getItemViewType(position);
  }


  public int getGroupViewType(int position, ExpandableGroup group) {
    return super.getItemViewType(position);
  }


  public boolean isGroup(int viewType) {
    return viewType == ExpandableListPosition.GROUP;
  }


  public boolean isChild(int viewType) {
    return viewType == ExpandableListPosition.CHILD;
  }
}
